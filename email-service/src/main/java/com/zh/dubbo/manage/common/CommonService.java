package com.zh.dubbo.manage.common;

import com.zh.dubbo.entity.Config;
import com.zh.dubbo.enttity.EmailTemplate;

import java.util.List;

/**
 * Created by 70214 on 2017/6/3.
 */
public interface CommonService {
    /**
     * 根据前缀获取相应服务商email配置
     * @param type 环境类型
     * @return
     * @throws Exception
     */
    public List<Config> getConfigList(String type) throws Exception;

    /**
     * 根据nid获取模版对象
     * @param nid
     * @return
     * @throws Exception
     */
        public EmailTemplate getTemplateByNid(String nid) throws Exception;
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
