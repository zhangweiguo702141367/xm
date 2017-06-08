package com.zh.dubbo.facade.sms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zh.dubbo.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */
@Service
public class SmsFacade {
    @Reference
    SmsService smsService;
    public String test1(){
        return smsService.test();
    }
    public String sendSms(Map<String,Object> params){
        try {
            smsService.sendCodeSMS(1, params);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return null;
    }
    public PageInfo getSendRecords(Map<String,Object> params){
        try{
            return smsService.getSendRecords(params);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
