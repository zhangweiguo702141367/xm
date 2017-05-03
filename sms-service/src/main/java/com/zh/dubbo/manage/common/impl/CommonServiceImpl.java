package com.zh.dubbo.manage.common.impl;

import com.zh.dubbo.dao.CommonDao;
import com.zh.dubbo.entity.SmsConfig;
import com.zh.dubbo.entity.SmsTemplate;
import com.zh.dubbo.manage.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
@Service
public class CommonServiceImpl implements CommonService{
    @Autowired
    private CommonDao commonDao;
    @Override
    public List<SmsConfig> getConfigList(String type) throws Exception {
        return commonDao.getConfigList(type);
    }

    @Override
    public String getConfigByNid(String nid, List<SmsConfig> configs) throws Exception {
        if(configs == null || configs.size() == 0){
            throw new Exception("短信配置列表不能为空");
        }
        if(nid == null || "".equals(nid)){
            throw new Exception("nid不能为空");
        }
        String value = "";
        for (SmsConfig smsConfig:configs) {
            String smsNid = smsConfig.getNid();
            if(nid.equals(smsNid)){
                value = smsConfig.getValue();
            }
        }
        if("".equals(value)){
            throw new Exception("未找到"+nid+"对应的值");
        }
        return value;
    }

    @Override
    public SmsTemplate getTemplateByNid(String nid) throws Exception {
        return commonDao.getSmsTemplateByNid(nid);
    }


}
