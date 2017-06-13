package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.core.shiro.cache.VCache;
import com.zh.dubbo.core.shiro.mapper.manage.auth.AuthService;
import com.zh.dubbo.core.shiro.tooken.manager.TokenManager;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.fo.MemberServiceFo;
import com.zh.dubbo.fo.SmsServiceFo;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.IPUtil;
import com.zh.dubbo.untils.RandomUtil;
import com.zh.dubbo.untils.RequestUtil;
import com.zh.dubbo.untils.security.SHAUtil;
import com.zh.dubbo.utils.UniqIdUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/12.
 */
@RestController
@RequestMapping("/inner/member/")
public class MemberController {
    @Autowired
    MemberServiceFo memberServiceFo;
    @Autowired
    SmsServiceFo smsServiceFo;
    @Autowired
    AuthService authService;

    /**
     * 注册用户第一步
     * 如果有被绑定的则提示用户解绑
     * @param request
     * @return
     */
    @PostMapping("isPhone")
    public RespData isPhone(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())) {
                throw new Exception("注册手机号不能为空!");
            }
            if(authService.isPhoneBind(params)){
                throw new Exception("手机号已被绑定!");
            }
            if(authService.isPhoneRegister(params)){
                throw new Exception("手机号已注册!");
            }
            respData.setStatus(RspConstants.SUCCESS);
            respData.setData("欢迎注册新用户");
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
    /**
     * 注册用户第二步
     * 注册用户获取验证码
     * @param request
     * @return
     */
    @PostMapping("getRegisterSmsCode")
    public RespData getRegisterSmsCode(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new Exception("注册手机号不能为空!");
            }
            params.put("sms_template_nid","register");
            Map<String,Object> template_params = new HashMap<>();
            String code = RandomUtil.sixCode();
            //生成6位验证码并存入redis中
            template_params.put("code",code);
            params.put("template_params",template_params);
            String rediskey = "register_phone_"+params.get("mobile_phone").toString();
            VCache.setex(rediskey,code,300);
            smsServiceFo.sendSms(params);
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("短信发送成功！");
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
    /**
     * 注册用户第三步
     * 注册用户
     * @param request
     * @return
     */
    @PostMapping("register")
    public RespData register(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("code") == null || "".equals(params.get("code").toString())){
                throw new Exception("请输入验证码！");
            }
            String inoutCode = params.get("code").toString();
            String rediskey = "register_phone_"+params.get("mobile_phone").toString();
            String code = VCache.get(rediskey);
            if(code == null || "".equals(code)){
                throw new Exception("验证码已过期，请重新获取验证码！");
            }
            if(!code.equals(inoutCode)){
                throw new Exception("验证码输入有误，请您重新输入验证码！");
            }
            String ip = IPUtil.getIpAddr(request);
            params.put("user_id", UniqIdUtil.getUserId());
            params.put("login_ip",ip);
            UUser user = memberServiceFo.register(params);
            if(user == null){
                throw new Exception("创建用户失败！");
            }
            UsernamePasswordToken token = new UsernamePasswordToken(user.getMobilePhone(), user.getPassword());
            SecurityUtils.getSubject().login(token);
            params.put("member_id",user.getId());
            //修改为密码后删除redis对应键值对
            VCache.delByKey(rediskey);
            try {
                //插入用户登录日志
                memberServiceFo.insertLogin(params);
            }catch (Exception e){

            }
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("用户创建成功！");
            return respData;
        }catch (Exception e){
            if(e.getMessage().indexOf("login_name")>0){
                respData.setMessage("注册手机号已存在！");
            }else if(e.getMessage().indexOf("mobile_phone")>0){
                respData.setMessage("注册手机号已存在！");
            }else if(e.getMessage().indexOf("nick_name")>0){
                respData.setMessage("昵称不能重复！");
            }else {
                respData.setMessage(e.getMessage());
            }
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
    /**
     * 手机忘记密码获取验证码
     * @param request
     * @return
     */
    @PostMapping("getSmsCode")
    public RespData sendSmsCode(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new Exception("用户名不能为空!");
            }
            params.put("sms_template_nid","register");
            Map<String,Object> template_params = new HashMap<>();
            String code = RandomUtil.sixCode();
            //生成6位验证码并存入redis中
            template_params.put("code",code);
            params.put("template_params",template_params);
            String rediskey = "forgetpassword_phone_"+params.get("mobile_phone").toString();
            VCache.setex(rediskey,code,300);
            smsServiceFo.sendSms(params);
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("短信发送成功！");
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
    /**
     * 忘记密码
     * 手机忘记密码
     * 暂时先处理为按登录名手机号去修改登录密码
     * @param request
     * @return
     */
    @PostMapping("forgetPassword")
    public RespData forgetPassword(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("login_name") == null || "".equals(params.get("login_name").toString())){
                throw new Exception("用户名不能为空!");
            }
            if(params.get("code") == null || "".equals(params.get("code").toString())){
                throw new Exception("请输入验证码！");
            }
            String inoutCode = params.get("code").toString();
            String rediskey = "forgetpassword_phone_"+params.get("login_name").toString();
            String code = VCache.get(rediskey);
            if(code == null || "".equals(code)){
                throw new Exception("验证码已过期，请重新获取验证码！");
            }
            if(!code.equals(inoutCode)){
                throw new Exception("验证码输入有误，请您重新输入验证码！");
            }
            UUser user = memberServiceFo.getMmeberInfoByLoginName(params.get("login_name").toString());
            if(user == null){
                throw new Exception("当前用户不存在！");
            }
            params.put("member_id",user.getId());
            memberServiceFo.updateMemberPassword(params);
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("修改密码成功！");
            //修改为密码后删除redis对应键值对
            VCache.delByKey(rediskey);
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }

    /**
     * 用户手机号登录
     * @param request
     * @return
     */
    @PostMapping("phoneLogin")
    public RespData phoneLogin(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("login_name") == null || "".equals(params.get("login_name").toString())){
                throw new  Exception("登录名不能为空！");
            }
            if(params.get("password") == null || "".equals(params.get("password").toString())){
                throw new Exception("登录密码不能为空！");
            }
            UUser member = memberServiceFo.getMmeberInfoByLoginName(params.get("login_name").toString());
            if(member == null){
                throw new Exception("当前用户不存在");
            }
            String salt = member.getSalt();
            String newPassword = SHAUtil.getPwd(params.get("password").toString(),salt,5);
            UsernamePasswordToken token = new UsernamePasswordToken(params.get("login_name").toString(), newPassword);
            SecurityUtils.getSubject().login(token);
            UUser user = TokenManager.getToken();
            params.put("member_id",user.getId());
            String ip = IPUtil.getIpAddr(request);
            params.put("login_ip",ip);
            try {
                //插入登录日志
                memberServiceFo.insertLogin(params);
            }catch (Exception e){

            }
//            System.out.println("aaaaaaaaaa==="+SecurityUtils.getSubject().getPrincipal().toString());
            System.out.println(TokenManager.getToken().toString());
            respData.setData("登录成功！");
            respData.setStatus(RspConstants.SUCCESS);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
    @PostMapping("logout")
    public RespData logout(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            TokenManager.logout();
            respData.setData("退出成功！");
            respData.setStatus(RspConstants.SUCCESS);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }

}
