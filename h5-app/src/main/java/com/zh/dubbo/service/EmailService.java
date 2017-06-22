package com.zh.dubbo.service;

import java.util.Map;
import java.util.Objects;

/**
 * Created by 70214 on 2017/6/3.
 */
public interface EmailService {
    /**
     * 单个邮件发送(支持自定义变量)
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> emailSingleSend(Map<String, Object> params) throws Exception;
    /**
     * 批量邮件发送(暂时不支持自定义变量)
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Objects> emailBatchSend(Map<String, Object> params) throws Exception;
    /**
     * 发送带附件邮件
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> emailSendAttachment(Map<String, Object> params) throws Exception;

    /**
     * 获取邮箱认证私钥
     * @return
     * @throws Exception
     */
    public String getPrivateRsa() throws Exception;
    /**
     * 获取邮箱认证公钥
     * @return
     * @throws Exception
     */
    public String getPublicRsa() throws Exception;
}
