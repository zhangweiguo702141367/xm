package com.zh.dubbo.dao;

import com.zh.dubbo.entity.Config;
import com.zh.dubbo.enttity.EmailTemplate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 70214 on 2017/6/3.
 */
@Component
@Mapper
public interface CommonDao {
    /**
     * 根据前缀获取相应服务商email配置
     * @param type
     * @return
     */
    @Results({
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "nid", column = "nid"),
            @Result(property = "name", column = "name"),
            @Result(property = "value", column = "value"),
    })
    @Select("SELECT * FROM zh_system.sys_email_conf WHERE nid LIKE CONCAT(#{type},'%')")
    List<Config> getConfigList(@Param("type") String type);

    /**
     * 根据模版nid获取模版信息
     * @param nid
     * @return
     */
    @Results({
            @Result(property = "templateNid", column = "template_nid"),
            @Result(property = "templeteName", column = "templete_name"),
            @Result(property = "templeteDesc", column = "templete_desc"),
            @Result(property = "templeteValue", column = "templete_value"),
            @Result(property = "templeteStatus", column = "templete_status"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT * FROM sys_email_templete WHERE template_nid=#{nid}")
    EmailTemplate getSmsTemplateByNid(@Param("nid") String nid);
}
