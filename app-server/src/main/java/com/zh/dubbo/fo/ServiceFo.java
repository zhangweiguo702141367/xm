package com.zh.dubbo.fo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zh.dubbo.service.SmsService;
import org.springframework.stereotype.Service;

/**
 * Created by 70214 on 2017/4/23.
 */
@Service
public class ServiceFo {
    @Reference
    SmsService smsService;
    public String test1(){
        return smsService.test();
    }
}
