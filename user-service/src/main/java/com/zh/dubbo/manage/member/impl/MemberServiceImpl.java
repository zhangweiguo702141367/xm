package com.zh.dubbo.manage.member.impl;

import com.zh.dubbo.dao.MemberDao;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.manage.member.MemberService;
import com.zh.dubbo.untils.*;
import com.zh.dubbo.untils.security.SHAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUser insertUser(Map<String, Object> params) throws Exception {

        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("user_id") == null || "".equals(params.get("user_id").toString())){
            throw new Exception("用户系统Id不能为空！");
        }
        if(params.get("nick_name") == null || "".equals(params.get("nick_name").toString())){
            throw new Exception("用户昵称不能为空！");
        }
        if(params.get("login_ip") == null || "".equals(params.get("nick_name").toString())){
            throw new Exception("用户注册IP不能为空！");
        }
//        if(params.get("login_name") == null || "".equals(params.get("login_name").toString())){
//            throw new Exception("登陆名不能为空！");
//        }
        String nickName = params.get("nick_name").toString();
        //正则校验昵称只能为因为字符
        if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
            throw new Exception("注册手机号不能为空");
        }
        if(params.get("password") == null || "".equals(params.get("password").toString())){
            throw new Exception("登录密码不能为空！");
        }
        String mobilePhone = params.get("mobile_phone").toString();
        //正则校验手机号格式
        if(!MatchUtil.checkPhone(mobilePhone)){
            throw new Exception("请输入正确的手机号！");
        }
//        String login_name = params.get("login_name").toString();
//        if(MatchUtil.checkPhone(login_name)){
//            login_name = login_name+"@mobile.com";
//        }else{
//            if(!MatchUtil.checkEmail(login_name)){
//                throw new Exception("请输入合法的邮箱！");
//            }
//        }
        //解除绑定当前手机号对应的用户
        memberDao.updateMemeberInfoFreeByPhone(mobilePhone);

        String salt = RandomUtil.getStringRandom(7);
        if("".equals(salt)){
            throw new Exception("加密盐生成异常！");
        }
        String spreadID = UUIDUtil.getUUID();
        Map<String,Object> member = new HashMap<>();
        member.put("userId",params.get("user_id"));
        member.put("salt",salt);
        member.put("password", SHAUtil.getPwd(params.get("password").toString(),salt,5));
        member.put("loginName",mobilePhone);
        member.put("nickName",nickName);
        member.put("mobilePhone",mobilePhone);
        member.put("headImage","http://www.lxiaomei.com/nginx-logo.png");
        member.put("isMobile",1);
        member.put("isEmail",-1);
        member.put("isIdentity",-1);
        member.put("lastLoginTime",DateUtil.getTime());
        member.put("lastLoginDate",DateUtil.getDate());
        member.put("registerTime",DateUtil.getTime());
        member.put("registerDate",DateUtil.getDate());
        member.put("spreadId",spreadID);
        member.put("roleId",1);
//        if(!login_name.endsWith("@mobile.com")){
//            member.put("email",params.get("login_name"));
//        }
        member.put("addTime",DateUtil.getCurrentTime());
        //创建用户
        memberDao.insertMember(member);
        logger.error("member===="+member.toString());
       //插入用户注册记录表
        member.put("lastMobile",mobilePhone);
        member.put("updateMobile",mobilePhone);
        memberDao.insertPhoneRecording(member);
        //获取用户信息
        UUser user = memberDao.getMemberInfoById(member.get("memberId").toString());
        user.setSalt(null);
        user.setPassword(null);
        return user;
    }

    @Override
    public void insertLoginLog(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("member_id") == null || "".equals(params.get("member_id").toString())){
            throw new Exception("用户Id不能为空！");
        }
        //获取用户Id
        String memberId = params.get("member_id").toString();
        if(params.get("login_ip") == null || "".equals(params.get("login_ip").toString())){
            throw new Exception("登录IP不能为空！");
        }
        String loginIp = params.get("login_ip").toString();
        Map<String,Object> data = new HashMap<>();
        data.put("memberId",memberId);
        data.put("loginIp",loginIp);
        data.put("loginTime", DateUtil.getTime());
        data.put("loginDate", DateUtil.getDate());
        data.put("addTime",DateUtil.getCurrentTime());

        memberDao.insertLoginLog(data);
    }

    @Override
    public void updateMemberPassword(Map<String, Object> params) throws Exception {
           if(params == null || params.size() == 0){
               throw new Exception("参数列表不能为空！");
           }
           if(params.get("type") == null || "".equals(params.get("type").toString())){
               throw new Exception("操作类型不能为空！");
           }
           if(params.get("memebr_id") == null || "".equals(params.get("member_id").toString())){
               throw new Exception("用户Id不能为空！");
           }
           String member_id = params.get("member_id").toString();
           //判断这个用户存在否
           UUser memberInfo = memberDao.getMemberInfoById(member_id);
           if(memberInfo == null){
               throw new Exception("用户信息有误,请稍后重试！");
           }
           String password = "";
           String type = params.get("type").toString();
           if("1".equals(type)){//如果是1则为忘记密码
               if(params.get("password") == null || "".equals(params.get("password").toString())){
                   throw new Exception("新密码不能为空！");
               }
               //校验密码规则只能为数字字母和特殊字符 不能为汉字
               if(MatchUtil.is_chinese(params.get("password").toString())){
                   throw new Exception("密码不能包含汉字请重新输入密码");
               }
               password = params.get("password").toString();
           }else if("2".equals(type)){//如果2则表示重置密码
               if(params.get("old_password") == null || "".equals(params.get("old_password").toString())){
                   throw new Exception("旧密码不能为空！");
               }
               if(params.get("first_password") == null || "".equals(params.get("first_password").toString())){
                   throw new Exception("第一次密码不能为空！");
               }
               if(params.get("second_password") == null || "".equals(params.get("second_password").toString())){
                   throw new Exception("第二次密码不能为空！");
               }
               String first_password = params.get("first_password").toString();
               String second_password = params.get("second_password").toString();
               String old_password = params.get("old_password").toString();
               if(first_password.equals(second_password)){
                   throw new Exception("两次密码不一致，请您重新输入！");
               }
               //校验密码不能为汉字
               if(MatchUtil.is_chinese(first_password)){
                   throw new Exception("密码不能包含汉字请重新输入密码");
               }
               if(MatchUtil.is_chinese(old_password)){
                   throw new Exception("密码不能包含汉字请重新输入密码");
               }
               //旧密码的随机盐
               String old_salt = memberInfo.getSalt();
               String oldpwd = SHAUtil.getPwd(old_password,old_salt,5);
               if(!oldpwd.equals(memberInfo.getPassword())){
                   throw new Exception("旧密码不正确，请输入正确的旧密码");
               }
               password = first_password;

           }
        String salt = RandomUtil.getStringRandom(7);
        if("".equals(salt)){
            throw new Exception("加密盐生成异常！");
        }
        String newPassword = SHAUtil.getPwd(password,salt,5);
        memberDao.updateMemberByPasswordSaltMemberId(newPassword,salt,member_id);
    }

    @Override
    public UUser memberLogin(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new  Exception("参数列表不能为空！");
        }
        if(params.get("login_name") == null || "".equals(params.get("login_name").toString())){
            throw new Exception("用户登录名不能为空！");
        }
        if(params.get("password") == null || "".equals(params.get("password").toString())){
            throw new Exception("用户密码不能为空！");
        }
        String login_name = params.get("login_name").toString();
        String password = params.get("password").toString();
        Map<String,Object> saltMap = memberDao.getSaltByLoginName(login_name);
        if(saltMap == null || saltMap.size() == 0){
            throw new Exception("当前用户不存在");
        }
        String salt = saltMap.get("salt").toString();
        String newPassword = SHAUtil.getPwd(password,salt,5);
        UUser member = memberDao.getMemberInfoByUsernameAndPassword(login_name,newPassword);
        if(member == null){
            throw new Exception("用户名密码不一致！请重新登录");
        }
        member.setPassword(null);
        member.setSalt(null);
        return member;
    }


}
