package com.zh.dubbo.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/17.
 */
public interface BaiduService {
    /**
     * 根据IP去获取当前用户所在位置
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> getLocationByIP(Map<String, Object> params) throws Exception;
}
