package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.service.SpreadUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/7.
 */
@Service(protocol = { "dubbo" })
public class SpreadUserServiceImpl implements SpreadUserService {
    @Autowired
    SpreadUserService spreadUserService;
    @Override
    public void insertSpread(Map<String, Object> params) throws Exception {
        spreadUserService.insertSpread(params);
    }
}
