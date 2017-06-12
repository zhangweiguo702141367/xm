package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.core.shiro.mapper.manage.auth.AuthService;
import com.zh.dubbo.core.shiro.tooken.manager.TokenManager;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.fo.MemberServiceFo;
import com.zh.dubbo.fo.SmsServiceFo;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.RandomUtil;
import com.zh.dubbo.untils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/12.
 */
@RestController
public class MemberController {
    @Autowired
    MemberServiceFo memberServiceFo;
    @Autowired
    SmsServiceFo smsServiceFo;
    @Autowired
    AuthService authService;
    /**
     * 注册获取验证码
     * @param request
     * @return
     */
    @GetMapping("getSmsCode")
    public RespData sendSmsCode(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, String> paramMap = RequestUtil.getQueryMap(request, "utf-8");
            Map<String,Object> params = new HashMap<>();
            params.putAll(paramMap);
            if(paramMap == null || paramMap.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(paramMap.get("mobile_phone") == null || "".equals(paramMap.get("mobile_phone").toString())){
                throw new Exception("用户名不能为空!");
            }
            Boolean isPhone = authService.isPhoneAuth(params);
            if(isPhone){
                throw new Exception("手机号已被注册或绑定！");
            }
            params.put("sms_template_nid","register");
            Map<String,Object> template_params = new HashMap<>();
            String code = RandomUtil.sixCode();
            //生成6位验证码并存入redis中
            template_params.put("code",code);
            params.put("template_params",template_params);
            smsServiceFo.sendSms(params);
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("短信发送成功！");
            return respData;
        }catch (Exception e){
            respData.setMessage("短信发送失败");
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
    /**
     * 忘记密码
     * @param request
     * @return
     */
    @GetMapping("forgetPassword")
    public RespData forgetPassword(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, String> paramMap = RequestUtil.getQueryMap(request, "utf-8");
            Map<String,Object> params = new HashMap<>();
            params.putAll(paramMap);
            if(paramMap == null || paramMap.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(paramMap.get("login_name") == null || "".equals(paramMap.get("login_name").toString())){
                throw new Exception("用户名不能为空!");
            }
            UUser user = memberServiceFo.getMmeberInfoByLoginName(paramMap.get("login_name").toString());
            if(user == null){
                throw new Exception("当前用户不存在！");
            }
            params.put("member_id",user.getId());
            memberServiceFo.updateMemberPassword(params);
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("修改密码成功！");
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }

}
