package com.zh.dubbo.sqlbuild;

import com.zh.dubbo.untils.StringUtils;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/4.
 */
public class SmsSqlBuild {
    /**
     * 如果有facilitator 则加入条件
     * 如果有env 则加入条件
     * 如果有mobile则加入条件
     * 如果有status 则加入条件
     * 如果有日期(暂时不考虑)
     * @param params
     * @return
     */
    public String queryPageByParams(final Map<String,Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM sys_sms_log WHERE 1=1");
        if(params == null || params.size() == 0){
            return sql.toString();
        }
        if(params.get("facilitator") != null && !"".equals(params.get("facilitator"))){
            sql.append(" AND facilitator=").append(params.get("facilitator").toString());
        }
        if(params.get("env") != null && !"".equals(params.get("env").toString())){
            sql.append(" AND env=").append(params.get("env").toString());
        }
        if(params.get("mobile") != null && !"".equals(params.get("mobile").toString())){
            sql.append(" AND mobile=").append(params.get("mobile").toString());
        }
        if(params.get("status") != null && !"".equals(params.get("status").toString())){
            sql.append(" AND status=").append(params.get("status").toString());
        }
        System.out.println("sql==="+sql.toString());
         return sql.toString();

    }
}
