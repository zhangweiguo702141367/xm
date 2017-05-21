package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.manage.qiniu.QNService;
import com.zh.dubbo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by 70214 on 2017/5/20.
 */
@Service(protocol = { "dubbo" })
public class ImageServiceImpl implements ImageService{
    @Autowired
    QNService qnService;
    @Override
    public Map<String, Object> upload(Map<String, Object> params) throws Exception {
        return qnService.upload(params);
    }

    @Override
    public String download(Map<String, Object> params) throws Exception {
        return qnService.download(params);
    }
}
