package com.zh.dubbo.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/7.
 */
public interface SpreadUserService {
    /**
     * 建立推广人关系
     * @param params
     * @throws Exception
     */
    public void insertSpread(Map<String,Object> params) throws Exception;
}
