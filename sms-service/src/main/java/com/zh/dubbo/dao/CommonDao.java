package com.zh.dubbo.dao;

import com.zh.dubbo.entity.SmsConfig;
import com.zh.dubbo.entity.SmsTemplate;
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
    @Select("SELECT * FROM zh_system.sys_sms_conf WHERE nid LIKE CONCAT(#{type},'%')")
    List<SmsConfig> getConfigList(@Param("type") String type);
    @Results({
            @Result(property = "smsTemplateId", column = "sms_template_id"),
    })
    @Select("SELECT * FROM sys_sms_template WHERE nid=#{nid}")
    SmsTemplate getSmsTemplateByNid(@Param("nid") String nid);

}
