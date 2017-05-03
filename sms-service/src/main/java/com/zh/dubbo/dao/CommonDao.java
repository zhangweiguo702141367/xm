package com.zh.dubbo.dao;

import com.zh.dubbo.entity.SmsConfig;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
@Component
@Mapper
public interface CommonDao {
    @Results({
            @Result(property = "facilitator", column = "facilitator"),
            @Result(property = "nid", column = "nid"),
            @Result(property = "name", column = "name"),
            @Result(property = "value", column = "value"),
    })
    @Select("SELECT * FROM zh_system.sys_sms_conf")
    List<SmsConfig> getConfigList(@Param("type") String type);

    @Select("SELECT * FROM sys_sms_conf WHERE nid=#{nid}")
    SmsConfig getSmsConfigByNid(@Param("nid") String nid);
}
