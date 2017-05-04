package com.zh.dubbo.service;



import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by 70214 on 2017/4/23.
 */
public interface SmsService {
    public String test();
    public Map<String,Object> sendCodeSMS(int facilitator, Map<String,Object> params) throws Exception;
    public Map<String,Object> sendVoiceSMS(int facilitator,Map<String,Object> params) throws Exception;
    public PageInfo getSendRecords(Map<String,Object> params) throws Exception;
}
