package com.zh.dubbo.manage.common.impl;

import com.zh.dubbo.dao.CommonDao;
import com.zh.dubbo.entity.Config;
import com.zh.dubbo.enttity.EmailTemplate;
import com.zh.dubbo.manage.common.CommonService;
import com.zh.dubbo.untils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 70214 on 2017/6/3.
 */
@Service
public class CommonServiceImpl implements CommonService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CommonDao commonDao;
    @Override
    public List<Config> getConfigList(String type) throws Exception {
        return commonDao.getConfigList(type);
    }

    @Override
    public EmailTemplate getTemplateByNid(String nid) throws Exception {
        return commonDao.getSmsTemplateByNid(nid);
    }

    @Override
    public String getPrivateRsa() throws Exception {
        return StringUtils.getConfFromList(this.getConfigList("aliyun_"),"aliyun_private_rsa");
    }

    @Override
    public String getPublicRsa() throws Exception {
        return StringUtils.getConfFromList(this.getConfigList("aliyun_"),"aliyun_prublic_rsa");
    }
}
