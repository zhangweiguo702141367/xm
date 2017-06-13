package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.core.shiro.cache.VCache;
import com.zh.dubbo.core.shiro.mapper.manage.auth.AuthService;
import com.zh.dubbo.core.shiro.tooken.manager.TokenManager;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.fo.SmsServiceFo;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.RandomUtil;
import com.zh.dubbo.untils.RequestUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/inner/auth/")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    SmsServiceFo smsServiceFo;

    /**
     * 判断手机是否被绑定
     * @param request
     * @return
     */
    @PostMapping("isAuthPhone")
    public RespData isAuthPhone(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())) {
                throw new Exception("认证手机号不能为空!");
            }
            if(authService.isPhoneBind(params)){
                throw new Exception("手机号已被绑定!");
            }
            respData.setStatus(RspConstants.SUCCESS);
            respData.setData("绑定手机");
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
    @PostMapping("sendAuthPhoneCode")
    public RespData sendAuthPhoneCode(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new Exception("认证手机号不能为空!");
            }
            params.put("sms_template_nid","register");
            Map<String,Object> template_params = new HashMap<>();
            String code = RandomUtil.sixCode();
            //生成6位验证码并存入redis中
            template_params.put("code",code);
            params.put("template_params",template_params);
            String rediskey = "auth_phone_"+params.get("mobile_phone").toString();
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
    @PostMapping("authPhone")
    public RespData authPhone(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new Exception("认证手机号不能为空!");
            }
            if(params.get("code") == null || "".equals(params.get("code").toString())){
                throw new Exception("请输入验证码！");
            }
            String inoutCode = params.get("code").toString();
            String rediskey = "auth_phone_"+params.get("mobile_phone").toString();
            String code = VCache.get(rediskey);
            if(code == null || "".equals(code)){
                throw new Exception("验证码已过期，请重新获取验证码！");
            }
            if(!code.equals(inoutCode)){
                throw new Exception("验证码输入有误，请您重新输入验证码！");
            }
            long member_id = TokenManager.getUserId();
            params.put("member_id",member_id);
            params.put("member_phone",params.get("mobile_phone").toString());
            UUser user = authService.phoneAuth(params);

            UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getPassword());
            SecurityUtils.getSubject().login(token);
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("手机绑定成功！");
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
}
