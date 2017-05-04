package com.zh.dubbo.service;


import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by 70214 on 2017/5/2.
 */
public interface SmsService {
    public String test();

    /**
     * 发送短信(包含验证码 和 通知短信)
     * @param facilitator 服务商(1、阿里大于，2、sendCloud)
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> sendCodeSMS(int facilitator,Map<String,Object> params) throws Exception;

    /**
     * 发送语音短信
     * @param facilitator 服务商(1、阿里大于是，2、sendCloud)
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> sendVoiceSMS(int facilitator,Map<String,Object> params) throws Exception;

    /**
     * 获取短信发送记录
     * @param params
     * @return
     * @throws Exception
     */
    public PageInfo getSendRecords(Map<String,Object> params) throws Exception;
}
