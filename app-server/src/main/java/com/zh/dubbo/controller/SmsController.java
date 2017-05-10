package com.zh.dubbo.controller;

import com.github.pagehelper.PageInfo;
import com.zh.dubbo.core.shiro.cache.VCache;
import com.zh.dubbo.fo.ServiceFo;
import com.zh.dubbo.untils.IPUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 70214 on 2017/4/23.
 */
@RestController
public class SmsController {
    @Autowired
    ServiceFo serviceFo;
    @GetMapping("/login")
    public String test1(HttpServletRequest request){
        Session session = SecurityUtils.getSubject().getSession();
        String test1 = "login this request ip +"+ IPUtil.getIpAddr(request);
//        VCache.set("zhangsan","saaa");
        return VCache.get("zhangsan");
    }
    @GetMapping("/test1")
    public String login(HttpServletRequest request){

        String result = "test1 this request ip +"+ IPUtil.getIpAddr(request);
        return result;
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
        return serviceFo.sendSms(params);
    }

    @GetMapping("/getSmsLogs")
    public PageInfo getSmsLogs(){
        Map<String,Object> params = new HashMap<String,Object>();
        return serviceFo.getSendRecords(params);
    }
}
