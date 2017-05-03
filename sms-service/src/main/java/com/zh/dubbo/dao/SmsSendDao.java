package com.zh.dubbo.dao;

import com.zh.dubbo.entity.SmsLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/5/3.
 */
@Mapper
@Component
public interface SmsSendDao {

    @Insert("INSERT INTO sys_sms_log(mobile,msg,facilitator,env,create_time,status) VALUES(#{mobile},#{msg},#{facilitator},#{env},#{createTime},#{status})")
    public int insertSmsLog(SmsLog smsLog);
}
