package com.zh.dubbo.manage.aliyun;

import java.util.Map;
import java.util.Objects;

/**
 * Created by 70214 on 2017/6/3.
 */
public interface EmailAliyunService {
    /**
     * 单个邮件发送(支持自定义变量)
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> emailSingleSend(Map<String,Object> params) throws Exception;
    /**
     * 批量邮件发送(暂时不支持自定义变量)
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Objects> emailBatchSend(Map<String,Object> params) throws Exception;
    /**
     * 发送带附件邮件
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> emailSendAttachment(Map<String,Object> params) throws Exception;
}
