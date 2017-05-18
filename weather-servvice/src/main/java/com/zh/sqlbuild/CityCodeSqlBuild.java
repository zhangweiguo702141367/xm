package com.zh.sqlbuild;

import com.zh.entity.CityCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
@Component
public class CityCodeSqlBuild {
    public String insetCityCodesBatch(final Map map){
        List<CityCode> citycodes = (List)map.get("cityCodes");
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO zh_system.sys_weather_code ");
        sql.append("(citycode,cityname,pinyin,province,create_time) ");
        sql.append("VALUES ");
        for (CityCode cityCode:citycodes) {
            sql.append("(");
//            sql.append("null").append(",");
            sql.append("'").append(cityCode.getCitycode()).append("',");
            sql.append("'").append(cityCode.getCityname()).append("',");
            sql.append("'").append(cityCode.getPinyin()).append("',");
            sql.append("'").append(cityCode.getProvince()).append("',");
            sql.append(cityCode.getCreateTime()).append(")");
            if((citycodes.indexOf(cityCode)+1)<citycodes.size()){
                sql.append(",");
            }
        }
        System.out.println("sql==="+sql.toString());
        return sql.toString();

    }
}
