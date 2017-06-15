package com.zh.dubbo.dao;

import com.zh.dubbo.entity.SysBaiDuConfig;
import com.zh.dubbo.entity.SysBaiduLog;
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

    @Insert("INSERT INTO zh_system.sys_baidu_log(ip,resp,province,city,city_code,point_x,point_y,create_time,member_id) VALUES(#{ip},#{resp},#{province},#{city},#{city_code},#{point_x},#{point_y},#{createTime},#{memberId})")
    int insertBaiDuLog(SysBaiduLog sysBaiduLog);
}
