package com.zh.dubbo.manage.member.impl;

import com.zh.dubbo.dao.MemberDao;
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
    public int insertUser(Map<String, Object> params) throws Exception {

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
        if(params.get("login_name") == null || "".equals(params.get("login_name").toString())){
            throw new Exception("登陆名不能为空！");
        }
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
        String login_name = params.get("login_name").toString();
        if(MatchUtil.checkPhone(login_name)){
            login_name = login_name+"@mobile.com";
        }else{
            if(!MatchUtil.checkEmail(login_name)){
                throw new Exception("请输入合法的邮箱！");
            }
        }
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
        member.put("loginName",login_name);
        member.put("nickName",nickName);
        member.put("mobilePhone",mobilePhone);
        member.put("headImage","http://www.lxiaomei.com/nginx-logo.png");
        member.put("isMobile",1);
        member.put("isEamil",-1);
        member.put("isIdentity",-1);
        member.put("lastLoginTime",DateUtil.getTime());
        member.put("lastLoginDate",DateUtil.getDate());
        member.put("registerTime",DateUtil.getTime());
        member.put("registerDate",DateUtil.getDate());
        member.put("spreadId",spreadID);
        member.put("roleId",1);
        if(!login_name.endsWith("@mobile.com")){
            member.put("email",params.get("login_name"));
        }
        member.put("addTime",DateUtil.getCurrentTime());
        //创建用户
        memberDao.insertMember(member);
        logger.error("member===="+member.toString());
       //插入用户注册记录表
        member.put("lastMobile",mobilePhone);
        member.put("updateMobile",mobilePhone);
        memberDao.insertPhoneRecording(member);
        return Integer.valueOf(member.get("memberId").toString());
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


}
