package com.zh.dubbo.dao;

import com.zh.dubbo.entity.CityCode;
import com.zh.dubbo.sqlbuild.CityCodeSqlBuild;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
@Component
@Mapper
public interface CityCodeDao {
    @InsertProvider(type= CityCodeSqlBuild.class,method = "insetCityCodesBatch")
    void insertCityCodesBatch(@Param("cityCodes") List<CityCode> cityCodes);

    @Select("SELECT * FROM zh_system.sys_weather_code WHERE cityname LIKE CONCAT(#{city},'%') AND province LIKE CONCAT(#{province},'%')")
    CityCode getCityCodeByProvinceAndCity(@Param("city") String city,@Param("province") String province);
}
