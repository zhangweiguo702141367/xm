package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.service.WeatherService;
import com.zh.dubbo.manage.citycode.CityCodeService;
import com.zh.dubbo.manage.json.OpenWeatherService;
import com.zh.dubbo.manage.xml.OpenWeatherXmlService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
@Service(protocol = { "dubbo" })
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    OpenWeatherService openWeatherService;
    @Autowired
    OpenWeatherXmlService openWeatherXmlService;
    @Autowired
    CityCodeService cityCodeService;
    @Override
    public Map<String, Object> getWeatherByCityCode(String citycode,String requestId) throws Exception {
        return openWeatherService.getWeatherByCityCode(citycode,requestId);
    }

    @Override
    public Map<String, Object> getWeatherByCityName(String cityName,String requestId) throws Exception {
        return openWeatherService.getWeatherByCityName(cityName,requestId);
    }

    @Override
    public Map<String, Object> getWeatherXmlByCityCode(String citycode,String requestId) throws Exception {
        return openWeatherXmlService.getWeatherXmlByCityCode(citycode,requestId);
    }

    @Override
    public Map<String, Object> getWeatherXmlByCityName(String cityName,String requestId) throws Exception {
        return openWeatherXmlService.getWeatherXmlByCityName(cityName,requestId);
    }

    @Override
    public String getCityCodeByProvinceAndCity(String province, String city,String requestId) throws Exception {
        return cityCodeService.getCityCodeByProvinceAndCity(province,city,requestId);
    }

    @Override
    public Map<String, Object> updateCityCode() throws Exception {
        return cityCodeService.updateCityCode();
    }
}
