package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 70214 on 2017/5/2.
 */
@Service(protocol = { "dubbo" })
public class MailServiceImpl implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    @Override
    public String test() {
        return "this is a test case";
    }
}
