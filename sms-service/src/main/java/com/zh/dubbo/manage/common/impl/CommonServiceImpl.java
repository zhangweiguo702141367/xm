package com.zh.dubbo.manage.common.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.dubbo.dao.CommonDao;
import com.zh.dubbo.dao.SmsSendDao;
import com.zh.dubbo.entity.SmsConfig;
import com.zh.dubbo.entity.SmsLog;
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
    @Autowired
    private SmsSendDao smsSendDao;
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

    @Override
    public PageInfo getSendRecords(Map<String, Object> params) throws Exception {
        if(params == null && params.size() == 0){
            throw new Exception("参数列表不能为空!");
        }
        //第几页
        Integer pageNum = params.get("pageNum")==null?1:Integer.valueOf(params.get("pageNum").toString());
        //每页显示多少个
        Integer pageSize = params.get("pageSize")==null?8:Integer.valueOf(params.get("pageSize").toString());
        PageHelper.startPage(pageNum, pageSize);
        //获取分页查询到的数据
        List<SmsLog> smsLogList = smsSendDao.queryPageByParams(params);
        //分装成PageInfo
        PageInfo smsLogPage = new PageInfo(smsLogList);
        return smsLogPage;
    }
}
