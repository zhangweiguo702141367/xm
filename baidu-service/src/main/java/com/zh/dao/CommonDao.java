package com.zh.dao;

import com.zh.entity.SysBaiDuConfig;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */
@Component
@Mapper
public interface CommonDao {
    @Results({
            @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT * FROM zh_system.sys_baidu_conf WHERE nid LIKE CONCAT(#{type},'%')")
    List<SysBaiDuConfig> getConfList(@Param("type") String type);

    @Results({
            @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT * FROM zh_system.sys_baidu_conf WHERE nid =#{nid}")
    SysBaiDuConfig getConfByNid(@Param("nid") String nid);
}
