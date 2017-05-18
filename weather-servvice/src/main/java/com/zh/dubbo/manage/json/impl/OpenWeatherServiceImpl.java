package com.zh.dubbo.manage.json.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.dubbo.manage.json.OpenWeatherService;
import com.zh.dubbo.utils.HttpClientUtil;
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
    @Override
    public Map<String, Object> getWeatherByCityCode(String citycode) throws Exception {
        if(citycode == null || "".equals(citycode)){
            throw new Exception("城市编码获取失败！");
        }
        if(waetherUrl == null || "".equals(waetherUrl)){
            throw new Exception("请求地址获取失败！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(waetherUrl).append("?citykey=").append(citycode);
//        Map<String,Object> params = new HashMap<>();
        String result = HttpClientUtil.get(sb.toString());
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
    }

    @Override
    public Map<String, Object> getWeatherByCityName(String cityName) throws Exception {
        if(cityName == null || "".equals(cityName)){
            throw new Exception("城市地址获取失败！");
        }
        if(waetherUrl == null || "".equals(waetherUrl)){
            throw new Exception("请求地址获取失败！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(waetherUrl).append("?city=").append(cityName);
        String result = HttpClientUtil.get(sb.toString());
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
    }
}
