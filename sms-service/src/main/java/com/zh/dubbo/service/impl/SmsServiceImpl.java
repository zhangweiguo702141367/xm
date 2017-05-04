package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.zh.dubbo.manage.alibaba.SmsSendAliService;
import com.zh.dubbo.manage.common.CommonService;
import com.zh.dubbo.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by 70214 on 2017/5/2.
 */
@Service(protocol = { "dubbo" })
public class SmsServiceImpl implements SmsService {
    @Autowired
    private SmsSendAliService smsSendAliService;
    @Autowired
    private CommonService commonService;
    @Override
    public String test() {
        return "this is a test for smsDubbo";
    }

    @Override
    public Map<String, Object> sendCodeSMS(int facilitator, Map<String, Object> params) throws Exception {
        System.out.println("enter here");
        if(facilitator == 1){
            return smsSendAliService.sendCodeSMS(params);
        }else{
            return null;
        }
    }

    @Override
    public Map<String, Object> sendVoiceSMS(int facilitator, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public PageInfo getSendRecords(Map<String, Object> params) throws Exception {
        return commonService.getSendRecords(params);
    }
}
