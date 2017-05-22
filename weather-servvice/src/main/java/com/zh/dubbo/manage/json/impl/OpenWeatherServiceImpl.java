package com.zh.dubbo.manage.json.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.dubbo.dao.WeatherLogDao;
import com.zh.dubbo.entity.WeatherLog;
import com.zh.dubbo.manage.json.OpenWeatherService;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
@Service
public class OpenWeatherServiceImpl implements OpenWeatherService{
    @Value("${weather.json.url}")
    private String waetherUrl;
    @Autowired
    WeatherLogDao weatherLogDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Map<String, Object> getWeatherByCityCode(String citycode,String requestId) throws Exception {
        if(citycode == null || "".equals(citycode)){
            throw new Exception("城市编码获取失败！");
        }
        if(waetherUrl == null || "".equals(waetherUrl)){
            throw new Exception("请求地址获取失败！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(waetherUrl).append("?citykey=").append(citycode);
//        Map<String,Object> params = new HashMap<>();
        WeatherLog weatherLog = new WeatherLog();
        weatherLog.setRequestFunction("getWeatherByCityCode");
        weatherLog.setRequestId(Integer.valueOf(requestId));
        weatherLog.setRequestParam("citykey="+citycode);
        weatherLog.setCreateTime(DateUtil.getCurrentTime());
        try{
        String result = HttpClientUtil.get(sb.toString());
        weatherLog.setResp(result);
        System.out.println("resultcitycode===="+result);
        if(result == null || "".equals(result)){
            throw new Exception("请求数据获取异常!");
        }
        Map<String, Object> params = new HashMap<>();
        try {
            params = (Map) JSONObject.parse(result);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("格式化请求结果异常！");
        }
        if(params == null){
            throw new Exception("未获取到正常数据！");
        }
        String status = params.get("status").toString();
        if(!"1000".equals(status)){
            throw new Exception("获取数据异常！");
        }
        System.out.println(JSONObject.parse(result));
        return (Map)params.get("data");
    }catch (Exception e){
        throw new Exception(e.getMessage());
    }finally {
        try {
            weatherLogDao.insertWeatherLog(weatherLog);
        }catch (Exception e1){
            logger.error(e1.getMessage());
        }
    }
}

    @Override
    public Map<String, Object> getWeatherByCityName(String cityName,String requestId) throws Exception {
        if(cityName == null || "".equals(cityName)){
            throw new Exception("城市地址获取失败！");
        }
        if(waetherUrl == null || "".equals(waetherUrl)){
            throw new Exception("请求地址获取失败！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(waetherUrl).append("?city=").append(cityName);
        WeatherLog weatherLog = new WeatherLog();
        weatherLog.setRequestFunction("getWeatherByCityName");
        weatherLog.setRequestId(Integer.valueOf(requestId));
        weatherLog.setRequestParam("citykey="+cityName);
        weatherLog.setCreateTime(DateUtil.getCurrentTime());
        try{
        String result = HttpClientUtil.get(sb.toString());
        weatherLog.setResp(result);
        System.out.println("resultcityname===="+result);
        if(result == null || "".equals(result)){
            throw new Exception("请求数据获取异常!");
        }
        Map<String, Object> params = new HashMap<>();
        try {
            params = (Map) JSONObject.parse(result);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("格式化请求结果异常！");
        }
        if(params == null){
            throw new Exception("未获取到正常数据！");
        }
        String status = params.get("status").toString();
        if(!"1000".equals(status)){
            throw new Exception("获取数据异常！");
        }
        System.out.println(JSONObject.parse(result));
        return (Map)params.get("data");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            try {
                weatherLogDao.insertWeatherLog(weatherLog);
            }catch (Exception e1){
                logger.error(e1.getMessage());
            }
        }
    }
}
