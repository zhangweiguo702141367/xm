package com.zh.dubbo.dao;

import com.zh.dubbo.entity.SmsLog;
import com.zh.dubbo.sqlbuild.SmsSqlBuild;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/3.
 */
@Mapper
@Component
public interface SmsSendDao {

    @Insert("INSERT INTO sys_sms_log(mobile,msg,facilitator,env,create_time,status) VALUES(#{mobile},#{msg},#{facilitator},#{env},#{createTime},#{status})")
    public int insertSmsLog(SmsLog smsLog);

    @SelectProvider(type = SmsSqlBuild.class,method = "queryPageByParams")
    List<SmsLog> queryPageByParams(Map<String,Object> params);

}
