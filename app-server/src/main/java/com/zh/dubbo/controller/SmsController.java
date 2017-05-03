package com.zh.dubbo.controller;

import com.zh.dubbo.fo.ServiceFo;
import com.zh.dubbo.untils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/test1")
    public String test1(HttpServletRequest request){

        System.out.println("this request ip +"+ IPUtil.getIpAddr(request));
        return serviceFo.test1();
    }
    @GetMapping("/sendSms")
    public String sendSms(){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile_phone","18811328493");
        params.put("SMS_TemplateId","SMS_52085322");
        return serviceFo.sendSms(params);
    }
}
