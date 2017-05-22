package com.zh.dubbo.sqlbuild;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/22.
 */
public class WeatherLogSqlBuild {
    /**
     * 如果有end_time 则加入条件
     * 如果有start_time则加入条件
     * 如果有日期(暂时不考虑)
     * @param params
     * @return
     */
    public String queryPageByParams(final Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM zh_system.sys_weather_log WHERE 1=1");
        if(params == null || params.size() == 0){
            return sql.toString();
        }
        if(params.get("start_time") != null && !"".equals(params.get("start_time"))){
            sql.append(" AND create_time>=").append(params.get("start_time").toString());
        }
        if(params.get("end_time") != null && !"".equals(params.get("end_time").toString())){
        sql.append(" AND create_time<=").append(params.get("end_time").toString());
        }
        System.out.println("sql==="+sql.toString());
        return sql.toString();

    }
}
