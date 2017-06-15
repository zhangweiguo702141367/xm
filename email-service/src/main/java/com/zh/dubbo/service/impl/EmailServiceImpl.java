package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.manage.aliyun.EmailAliyunService;
import com.zh.dubbo.manage.common.CommonService;
import com.zh.dubbo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;

/**
 * Created by 70214 on 2017/6/3.
 */
@Service(protocol = { "dubbo" })
public class EmailServiceImpl implements EmailService{
    @Autowired
    EmailAliyunService emailAliyunService;
    @Autowired
    CommonService commonService;
    @Override
    public Map<String, Object> emailSingleSend(Map<String, Object> params) throws Exception {
        return emailAliyunService.emailSingleSend(params);
    }

    @Override
    public Map<String, Objects> emailBatchSend(Map<String, Object> params) throws Exception {
        return emailAliyunService.emailBatchSend(params);
    }

    @Override
    public Map<String, Object> emailSendAttachment(Map<String, Object> params) throws Exception {
        return emailAliyunService.emailSendAttachment(params);
    }

    @Override
    public String getPrivateRsa() throws Exception {
        return commonService.getPrivateRsa();
    }

    @Override
    public String getPublicRsa() throws Exception {
        return commonService.getPublicRsa();
    }
}
