package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.service.EmailService;

import java.util.Map;
import java.util.Objects;

/**
 * Created by 70214 on 2017/6/3.
 */
@Service(protocol = { "dubbo" })
public class EmailServiceImpl implements EmailService{
    @Override
    public Map<String, Object> emailSingleSend(Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public Map<String, Objects> emailBatchSend(Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> emailSendAttachment(Map<String, Object> params) throws Exception {
        return null;
    }
}
