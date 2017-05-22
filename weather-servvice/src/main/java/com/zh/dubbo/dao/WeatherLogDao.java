package com.zh.dubbo.dao;

import com.zh.dubbo.entity.WeatherLog;
import com.zh.dubbo.sqlbuild.WeatherLogSqlBuild;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22.
 */
@Mapper
@Component
public interface WeatherLogDao {
    @Insert("INSERT INTO zh_system.sys_weather_log (request_param,request_id,request_function,resp,create_time) VALUES(#{requestParam},#{requestId},#{requestFunction},#{resp},#{createTime})")
    int insertWeatherLog(WeatherLog weatherLog);

    @SelectProvider(type = WeatherLogSqlBuild.class,method = "queryPageByParams")
    List<WeatherLog> queryPageByParams(Map<String,Object> params);
}
