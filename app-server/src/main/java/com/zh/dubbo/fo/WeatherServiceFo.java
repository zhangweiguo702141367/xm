package com.zh.dubbo.fo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zh.dubbo.core.shiro.cache.VCache;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.service.BaiduService;
import com.zh.dubbo.service.WeatherService;
import com.zh.dubbo.untils.RequestUtil;
import com.zh.dubbo.utils.UniqIdUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取用户所在地址天气状况
 * Created by Administrator on 2017/6/15.
 */
@Component
public class WeatherServiceFo {
    @Reference
    BaiduService baiduService;
    @Reference
    WeatherService weatherService;
    public Map<String,Object> getWeatherInfoByIp(String member_id,String ip) throws Exception{
        if("".equals(member_id) || "".equals(ip)){
            throw new Exception("缺少必要参数！");
        }
        Map<String,Object> baiduParams = new HashMap<>();
        baiduParams.put("member_id",member_id);
        baiduParams.put("ip",ip);
        Map<String,Object> baiduResp = baiduService.getLocationByIP(baiduParams);
        if(baiduResp == null || baiduResp.size() == 0){
            throw new Exception("获取地理位置异常！");
        }
        if(!RequestUtil.hasAllKey(baiduResp,new String[]{"province","city"})){
            throw new Exception("获取地理位置异常！");
        }
        String province = baiduResp.get("province").toString();
        String city = baiduResp.get("city").toString();
        String cityCode = weatherService.getCityCodeByProvinceAndCity(province,city);
        Map<String,Object> weather = weatherService.getWeatherXmlByCityCode(cityCode, UniqIdUtil.getWeatherRequestId());
        if(weather == null || weather.size() == 0){
            throw new ProcException("获取天气信息异常！");
        }
        weather.put("city",baiduResp.get("city"));
        return weather;
    }
}
