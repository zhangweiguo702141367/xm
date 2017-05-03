package com.zh.dubbo.manage.alibaba.impl;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.zh.dubbo.constant.SmsConstant;
import com.zh.dubbo.dao.CommonDao;
import com.zh.dubbo.dao.SmsSendDao;
import com.zh.dubbo.entity.SmsConfig;
import com.zh.dubbo.entity.SmsLog;
import com.zh.dubbo.manage.alibaba.SmsSendAliService;
import com.zh.dubbo.manage.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
@Service
public class SmsSendAliServiceImpl implements SmsSendAliService{
    @Value("${spring.profiles.active}")
    private String env;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private SmsSendDao smsSendDao;
    @Autowired
    private CommonService commonService;
    @Override
    public Map<String, Object> sendCodeSMS(Map<String, Object> params) throws Exception {
        System.out.println("enter there");
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空");
        }
        if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
            throw new Exception("手机号码不能为空");
        }
        if(params.get("SMS_TemplateId") == null || "".equals(params.get("SMS_TemplateId").toString())){
            throw  new Exception("短信模板不能为空");
        }
        if(env == null){
            throw new Exception("环境配置不能为空");
        }
        //阿里短信模板id
        String sms_tempalteId = params.get("SMS_TemplateId").toString();
        //发送短信手机号
        String mobile = params.get("mobile_phone").toString();
        List<SmsConfig> configs = commonService.getConfigList("dev");
        //阿里请求url
        String url = commonService.getConfigByNid(env+".url",configs);
        //阿里appKey
        String appkey = commonService.getConfigByNid(env+".appKey",configs);
        //阿里appSecret
        String secret = commonService.getConfigByNid(env+".appSecret",configs);
        //阿里短信签名
        String smsFreeSignName = commonService.getConfigByNid(env+".SmsFreeSignName",configs);
        SmsLog smsLog = new SmsLog();
        smsLog.setMobile(mobile);
        smsLog.setEnv(env);
        smsLog.setFacilitator(1);
        smsLog.setMsg(SmsConstant.ALI_SMS_63820610);
        smsLog.setCreateTime(new Date().getTime());
        //如果是测试环境则仅插入短信记录，不真正发送短信
        if("dev".equals(env)){
            smsLog.setStstus(1);
            smsSendDao.insertSmsLog(smsLog);
            return new HashMap<String,Object>();
        }
        try {
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("");
            req.setSmsType("normal");
            req.setSmsFreeSignName(smsFreeSignName);
            req.setSmsParamString("");
            req.setRecNum(mobile);
            req.setSmsTemplateCode(sms_tempalteId);
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            if(rsp.isSuccess()){
                smsLog.setStstus(1);
            }else{
                smsLog.setStstus(2);
            }
            smsSendDao.insertSmsLog(smsLog);
            System.out.println(rsp.getBody());
        }catch(Exception e){
            System.out.println(e.getMessage());
            smsLog.setStstus(2);
            smsSendDao.insertSmsLog(smsLog);
        }
        return null;
    }
}
