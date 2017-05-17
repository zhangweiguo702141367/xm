package com.zh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.manage.baiduMap.BaiDuMapService;
import com.zh.service.BaiduService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/17.
 */
@Service(protocol = { "dubbo" })
public class BaiduServiceImpl implements BaiduService {
    @Autowired
    BaiDuMapService baiDuMapService;
    @Override
    public Map<String, Object> getLocationByIP(Map<String, Object> params) throws Exception {
        return baiDuMapService.getLocationByIP(params);
    }
}
