package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.fo.SmsServiceFo;
import com.zh.dubbo.mapper.manage.auth.AuthService;
import com.zh.dubbo.token.TokenManage;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.RandomUtil;
import com.zh.dubbo.untils.RequestUtil;
import com.zh.dubbo.utils.JedisUtil;
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
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())) {
                throw new ProcException("认证手机号不能为空!");
            }
            if(authService.isPhoneBind(params)){
                throw new ProcException("手机号已被绑定!");
            }
            return RespData.create(RspConstants.SUCCESS,"绑定手机",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    @PostMapping("sendAuthPhoneCode")
    public RespData sendAuthPhoneCode(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new ProcException("认证手机号不能为空!");
            }
            params.put("sms_template_nid","register");
            Map<String,Object> template_params = new HashMap<>();
            String code = RandomUtil.sixCode();
            //生成6位验证码并存入redis中
            template_params.put("code",code);
            params.put("template_params",template_params);
            String rediskey = "auth_phone_"+params.get("mobile_phone").toString();
            JedisUtil.setex(rediskey,code,300);
            smsServiceFo.sendSms(params);
            return RespData.create(RspConstants.SUCCESS,"短信发送成功",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    @PostMapping("authPhone")
    public RespData authPhone(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            String authtoken = request.getAttribute("authtoken").toString();
            String authsign = request.getAttribute("authsign").toString();
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
                throw new ProcException("认证手机号不能为空!");
            }
            if(params.get("code") == null || "".equals(params.get("code").toString())){
                throw new ProcException("请输入验证码！");
            }
            String inoutCode = params.get("code").toString();
            String rediskey = "auth_phone_"+params.get("mobile_phone").toString();
            String code = JedisUtil.get(rediskey);
            if(code == null || "".equals(code)){
                throw new ProcException("验证码已过期，请重新获取验证码！");
            }
            if(!code.equals(inoutCode)){
                throw new ProcException("验证码输入有误，请您重新输入验证码！");
            }
            long member_id = Long.valueOf(request.getAttribute("userId").toString());
            params.put("member_id",member_id);
            params.put("member_phone",params.get("mobile_phone").toString());
            UUser user = authService.phoneAuth(params);
            //用户登录
            Map<String,Object> signMap = TokenManage.tokenUpdate(authtoken,authsign,user);
            request.setAttribute("authtoken",signMap.get("token"));
            request.setAttribute("authsign",signMap.get("sign"));
            //修改为密码后删除redis对应键值对
            JedisUtil.delByKey(rediskey);
            return RespData.create(RspConstants.SUCCESS,"手机绑定成功",null,DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
}
