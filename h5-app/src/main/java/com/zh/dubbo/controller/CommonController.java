package com.zh.dubbo.controller;

import com.github.pagehelper.PageInfo;
import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.SysPermissionInit;
import com.zh.dubbo.fo.SmsServiceFo;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 70214 on 2017/4/23.
 */
@RestController
public class CommonController {
    @Autowired
    SmsServiceFo smsServiceFo;

    /**
     * 用户未登录根据status前台跳转登录页面
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public RespData unlogin(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("authtoken",null);
        response.setHeader("authsign",null);
        return RespData.create(RspConstants.UNLOGIN,"未登录",null,DateUtil.getCurrentTime());
    }
    @GetMapping("/unlogin")
    public String test1(HttpServletRequest request){
        String result = "test1 this request ip +"+ IPUtil.getIpAddr(request);
        System.out.println("IPUtil===="+result);
        try {
//            UsernamePasswordToken token = new UsernamePasswordToken("18811328493", "112233");
//            SecurityUtils.getSubject().login(token);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return  "login failed";
        }
        return "login success";
    }
    @GetMapping("/unauthorized")
    public List<SysPermissionInit> unauthorized(HttpServletRequest request){
//        VCache.set("zhangsan","saaa");
//        return userServiceFo.getAllSysPermissions();
//        return "unauthorized";
        return null;
    }
    @GetMapping("/test1")
    public String login(HttpServletRequest request){
        String result = "test1 this request ip +"+ IPUtil.getIpAddr(request);
        System.out.println("IPUtil===="+result);
        try {
//            UsernamePasswordToken token = new UsernamePasswordToken("13834412691", "a123123");
//            SecurityUtils.getSubject().login(token);
        }catch (Exception e){
            return  "login failed";
        }
        return "login success";
    }
    //踢出用户
    @GetMapping(value="kickouting")
    public String kickouting() {

        return "kickouting";
    }

    //被踢出后跳转的页面
    @GetMapping(value="kickout")
    public String kickout() {
        return "kickout";
    }
    @GetMapping("/sendSms")
    public String sendSms(){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile_phone","18811328493");
        params.put("sms_template_id","SMS_52085322");
        params.put("sms_template_nid","register");
        Map<String,Object> templateParams = new HashMap<>();
        templateParams.put("code","1314258");
        params.put("template_params",templateParams);
        return smsServiceFo.sendSms(params);
    }

    @GetMapping("/getSmsLogs")
    public PageInfo getSmsLogs(){
        Map<String,Object> params = new HashMap<String,Object>();
        return smsServiceFo.getSendRecords(params);
    }
}
