package com.zh.dubbo.manage.alibaba;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface SmsSendAliService {
    /**
     * 发送短信(包含验证码 和 通知短信)
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> sendCodeSMS(Map<String,Object> params) throws Exception;
}
