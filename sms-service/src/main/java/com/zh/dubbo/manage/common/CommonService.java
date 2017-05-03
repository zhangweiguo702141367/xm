package com.zh.dubbo.manage.common;

import com.zh.dubbo.entity.SmsConfig;
import com.zh.dubbo.entity.SmsTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface CommonService {
    /**
     * 获取短信配置列表
     * @param type 环境类型
     * @return
     * @throws Exception
     */
    public List<SmsConfig> getConfigList(String type) throws Exception;

    /**
     * 根据nid获取相应参数
     * @param nid 配置项名称
     * @param configs 配置项列表
     * @return
     * @throws Exception
     */
    public String getConfigByNid(String nid,List<SmsConfig> configs) throws Exception;

    /**
     * 根据nid获取模版对象
     * @param nid
     * @return
     * @throws Exception
     */
    public SmsTemplate getTemplateByNid(String nid) throws Exception;
}
