package com.zh.dubbo.service;

import java.util.Map;

/**
 * Created by 70214 on 2017/5/20.
 */
public interface ImageService {
    /**
     * 上传文件至七牛服务器上
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> upload(Map<String,Object> params) throws Exception;

    /**
     * 下载文件从七牛服务器上
     * @param params
     * @return
     * @throws Exception
     */
    public String download(Map<String,Object> params) throws Exception;
}
