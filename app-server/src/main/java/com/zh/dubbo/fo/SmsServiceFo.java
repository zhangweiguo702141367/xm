package com.zh.dubbo.fo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zh.dubbo.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 70214 on 2017/4/23.
 */
@Service
public class SmsServiceFo {
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
