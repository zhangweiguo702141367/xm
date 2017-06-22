package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.fo.EmailServiceFo;
import com.zh.dubbo.fo.MemberServiceFo;
import com.zh.dubbo.fo.SmsServiceFo;
import com.zh.dubbo.mapper.manage.auth.AuthService;
import com.zh.dubbo.token.TokenManage;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.IPUtil;
import com.zh.dubbo.untils.RandomUtil;
import com.zh.dubbo.untils.RequestUtil;
import com.zh.dubbo.untils.security.SHAUtil;
import com.zh.dubbo.utils.JedisUtil;
import com.zh.dubbo.utils.UniqIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
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
    @Autowired
    EmailServiceFo emailServiceFo;
    /**
     * 注册用户第一步
     * 如果有被绑定的则提示用户解绑
     * @param request
     * @return
     */
    @PostMapping("isPhone")
    public RespData isPhone(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())) {
                throw new ProcException("注册手机号不能为空!");
            }
            if(authService.isPhoneRegister(params)){
                throw new ProcException("手机号已注册!");
            }
            if(authService.isPhoneBind(params)){
                throw new ProcException("手机号已被绑定!");
            }
            return RespData.create(RspConstants.SUCCESS,"欢迎注册新用户",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
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
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new ProcException("注册手机号不能为空!");
            }
            params.put("sms_template_nid","register");
            Map<String,Object> template_params = new HashMap<>();
            String code = RandomUtil.sixCode();
            //生成6位验证码并存入redis中
            template_params.put("code",code);
            params.put("template_params",template_params);
            String rediskey = "register_phone_"+params.get("mobile_phone").toString();
            JedisUtil.setex(rediskey,code,300);
            smsServiceFo.sendSms(params);
            return RespData.create(RspConstants.SUCCESS,"短信发送成功",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
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
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("code") == null || "".equals(params.get("code").toString())){
                throw new ProcException("请输入验证码！");
            }
            String inoutCode = params.get("code").toString();
            String rediskey = "register_phone_"+params.get("mobile_phone").toString();
            String code = JedisUtil.get(rediskey);
            if(code == null || "".equals(code)){
                throw new ProcException("验证码已过期，请重新获取验证码！");
            }
            if(!code.equals(inoutCode)){
                throw new ProcException("验证码输入有误，请您重新输入验证码！");
            }
            String ip = IPUtil.getIpAddr(request);
            params.put("user_id", UniqIdUtil.getUserId());
            params.put("login_ip",ip);
            UUser user = memberServiceFo.register(params);
            if(user == null){
                throw new Exception("创建用户失败！");
            }
            //做登录
            Map<String,Object> signMap = TokenManage.tokenCreate(user,ip);
            request.setAttribute("authtoken",signMap.get("token"));
            request.setAttribute("authsign",signMap.get("sign"));
            params.put("member_id",user.getId());
            //修改为密码后删除redis对应键值对
            JedisUtil.delByKey(rediskey);
            try {
                //插入用户登录日志
                memberServiceFo.insertLogin(params);
            }catch (Exception e){

            }
            return RespData.create(RspConstants.SUCCESS,"用户创建成功！",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            if(e.getMessage().indexOf("login_name")>0){
                return RespData.create(RspConstants.SERVICEERROR,"注册手机号已存在",null,DateUtil.getCurrentTime());
            }else if(e.getMessage().indexOf("mobile_phone")>0){
                return RespData.create(RspConstants.SERVICEERROR,"注册手机号已存在",null,DateUtil.getCurrentTime());
            }else if(e.getMessage().indexOf("nick_name")>0){
                return RespData.create(RspConstants.SERVICEERROR,"昵称不能重复",null,DateUtil.getCurrentTime());
            }
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    /**
     * 忘记密码第一步
     * 校验登录名是否存在
     * @param request
     * @return
     */
    @PostMapping("isRegister")
    public RespData isRegister(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("login_name") == null || "".equals(params.get("login_name").toString())) {
                throw new ProcException("登录名不能为空!");
            }
            if(authService.isPhoneRegister(params)){
                return RespData.create(RspConstants.SUCCESS,"存在当前用户",null,DateUtil.getCurrentTime());
            }
            return RespData.create(RspConstants.SERVICEERROR,"当前用户不存在",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    /**
     * 忘记密码第二步(手机找回形式)
     * 手机忘记密码获取验证码
     * @param request
     * @return
     */
    @PostMapping("getSmsCode")
    public RespData sendSmsCode(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("login_name") == null || "".equals(params.get("login_name").toString())){
                throw new ProcException("认证手机号不能为空!");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new ProcException("认证手机号不能为空!");
            }
            // 先根据登录名和用户认证手机号去判断当前用户是否合法
            //登录名
            String login_name = params.get("login_name").toString();
            //认证手机号
            String mobile_phone = params.get("mobile_phone").toString();
            Boolean isLeagal = memberServiceFo.isLegalByLoginNameAndMobilePhone(login_name,mobile_phone);
            if(!isLeagal){
                throw new ProcException("登录名和认证手机号不匹配,请输入正确的信息");
            }
            //发送短信验证码
            params.put("sms_template_nid","forget");
            Map<String,Object> template_params = new HashMap<>();
            String code = RandomUtil.sixCode();
            //生成6位验证码并存入redis中
            template_params.put("code",code);
            params.put("template_params",template_params);
            String rediskey = "forgetpassword_phone_"+params.get("mobile_phone").toString();
            JedisUtil.setex(rediskey,code,300);
            smsServiceFo.sendSms(params);
            return RespData.create(RspConstants.SUCCESS,"短信发送成功！",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
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
                throw new ProcException("登录名不能为空!");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new ProcException("认证手机号不能为空!");
            }
            // 先根据登录名和用户认证手机号去判断当前用户是否合法
            //登录名
            String login_name = params.get("login_name").toString();
            //认证手机号
            String mobile_phone = params.get("mobile_phone").toString();
            Boolean isLeagal = memberServiceFo.isLegalByLoginNameAndMobilePhone(login_name,mobile_phone);
            if(!isLeagal){
                throw new ProcException("登录名和认证手机号不匹配,请输入正确的信息");
            }
            if(params.get("code") == null || "".equals(params.get("code").toString())){
                throw new ProcException("请输入验证码！");
            }
            String inoutCode = params.get("code").toString();
            String rediskey = "forgetpassword_phone_"+params.get("mobile_phone").toString();
            String code = JedisUtil.get(rediskey);
            if(code == null || "".equals(code)){
                throw new ProcException("验证码已过期，请重新获取验证码！");
            }
            if(!code.equals(inoutCode)){
                throw new ProcException("验证码输入有误，请您重新输入验证码！");
            }
            UUser user = memberServiceFo.getMmeberInfoByLoginName(params.get("login_name").toString());
            if(user == null){
                throw new Exception("当前用户不存在！");
            }
            params.put("member_id",user.getId());
            params.put("type",1);
            memberServiceFo.updateMemberPassword(params);
            //修改为密码后删除redis对应键值对
            JedisUtil.delByKey(rediskey);
            return RespData.create(RspConstants.SUCCESS,"修改密码成功！",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    /**
     * 忘记密码(邮箱找回形式)第一步
     * @param request
     * @return
     */
    @PostMapping("emailForgetPassword")
    public RespData emailForgetPassword(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("login_name") == null || "".equals(params.get("login_name").toString())){
                throw new ProcException("登录名不能为空!");
            }
            if(params.get("email") == null || "".equals(params.get("email").toString())){
                throw new ProcException("认证手机号不能为空!");
            }
            // 先根据登录名和用户认证手机号去判断当前用户是否合法
            //登录名
            String login_name = params.get("login_name").toString();
            //认证邮箱
            String email = params.get("email").toString();
            Boolean isLeagal = memberServiceFo.isLegalByLoginNameAndEmail(login_name,email);
            if(!isLeagal){
                throw new ProcException("登录名和认证手机号不匹配,请输入正确的信息");
            }
            UUser user = memberServiceFo.getMmeberInfoByLoginName(login_name);
            if(user == null){
                throw new ProcException("用户信息异常");
            }
            String tokenAndSign = emailServiceFo.getEmailPwdSign(login_name,email);
            System.out.println("tokenAndSign============="+tokenAndSign);
            params.put("template_nid","forgetpassword");
            params.put("to_email",email);
            params.put("member_id",user.getId());
            Map<String,Object> template_param = new HashMap<String,Object>();
            template_param.put("tokenAndSign", tokenAndSign);
            params.put("template_param",template_param);
            emailServiceFo.sendAuthEmail(params);
            return RespData.create(RspConstants.SUCCESS,"邮件发送成功",null, DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    /**
     * 忘记密码(邮箱找回形式)第二步
     * @param request
     * @return
     */
    @PostMapping("emailChangePassword")
    public RespData emailChangePassword(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("token") == null || "".equals(params.get("token").toString())){
                throw new ProcException("签名不能为空!");
            }
            if(params.get("sign") == null || "".equals(params.get("sign").toString())){
                throw new ProcException("签名不能为空!");
            }
            if(params.get("password") == null || "".equals(params.get("password").toString())){
                throw new ProcException("登录密码不能为空!");
            }
            String token = URLDecoder.decode(params.get("token").toString(),"UTF-8");
            String sign = URLDecoder.decode(params.get("sign").toString(),"UTF-8");
            System.out.println("token============"+token);
            System.out.println("sign============="+sign);
            Map<String,Object> signMap = emailServiceFo.designEmailPwd(token,sign);
            // 先根据登录名和用户认证手机号去判断当前用户是否合法
            //登录名
            String login_name = signMap.get("login_name").toString();
            //认证手机号
            String email = signMap.get("email").toString();
            Boolean isLeagal = memberServiceFo.isLegalByLoginNameAndEmail(login_name,email);
            if(!isLeagal){
                throw new ProcException("登录名和认证邮箱不匹配");
            }
            UUser user = memberServiceFo.getMmeberInfoByLoginName(login_name);
            if(user == null){
                throw new ProcException("用户信息异常");
            }
            params.put("type",1);//修改密码
            params.put("member_id",user.getId());
            //修改密码
            memberServiceFo.updateMemberPassword(params);
            return RespData.create(RspConstants.SUCCESS,"密码修改成功",null, DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
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
                throw new  ProcException("登录名不能为空！");
            }
            if(params.get("password") == null || "".equals(params.get("password").toString())){
                throw new ProcException("登录密码不能为空！");
            }
            UUser member = memberServiceFo.getMmeberInfoByLoginName(params.get("login_name").toString());
            if(member == null){
                throw new ProcException("当前用户不存在");
            }
            String salt = member.getSalt();
            String newPassword = SHAUtil.getPwd(params.get("password").toString(),salt,5);
            params.put("password",newPassword);
            UUser user = memberServiceFo.phoneLogin(params);
            String ip = IPUtil.getIpAddr(request);
            params.put("login_ip",ip);
            //做登录
            Map<String,Object> signMap = TokenManage.tokenCreate(user,ip);
            request.setAttribute("authtoken",signMap.get("token"));
            request.setAttribute("authsign",signMap.get("sign"));
            params.put("member_id",user.getId());
            try {
                //插入登录日志
                memberServiceFo.insertLogin(params);
            }catch (Exception e){

            }
//          System.out.println("aaaaaaaaaa==="+SecurityUtils.getSubject().getPrincipal().toString());
            return RespData.create(RspConstants.SUCCESS,"登录成功",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    @PostMapping("logout")
    public RespData logout(HttpServletRequest request){
        try {
            String redisName = request.getAttribute("redisName").toString();
            TokenManage.tokenDelete(redisName);
            return RespData.create(RspConstants.SUCCESS,"退出成功",null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }

}
