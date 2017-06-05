package com.zh.dubbo.manage.spread;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/5.
 */
public interface SpreadService {
    /**
     * 建立推广人关系
     * @param params
     * @throws Exception
     */
    public void insertSpread(Map<String,Object> params) throws Exception;
}
