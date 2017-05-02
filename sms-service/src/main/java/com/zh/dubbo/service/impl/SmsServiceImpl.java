package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.Page;
import com.zh.dubbo.service.SmsService;

import java.util.Map;

/**
 * Created by 70214 on 2017/5/2.
 */
@Service(protocol = { "dubbo" })
public class SmsServiceImpl implements SmsService {
    @Override
    public String test() {
        return "this is a test for smsDubbo";
    }

    @Override
    public Map<String, Object> sendCodeSMS(int facilitator, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> sendVoiceSMS(int facilitator, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public Page getSendRecords(Map<String, Object> params) throws Exception {
        return null;
    }
}
