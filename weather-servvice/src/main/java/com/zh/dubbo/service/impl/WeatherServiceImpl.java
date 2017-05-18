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
    public Map<String, Object> getWeatherByCityCode(String citycode) throws Exception {
        return openWeatherService.getWeatherByCityCode(citycode);
    }

    @Override
    public Map<String, Object> getWeatherByCityName(String cityName) throws Exception {
        return openWeatherService.getWeatherByCityName(cityName);
    }

    @Override
    public Map<String, Object> getWeatherXmlByCityCode(String citycode) throws Exception {
        return openWeatherXmlService.getWeatherXmlByCityCode(citycode);
    }

    @Override
    public Map<String, Object> getWeatherXmlByCityName(String cityName) throws Exception {
        return openWeatherXmlService.getWeatherXmlByCityName(cityName);
    }

    @Override
    public String getCityCodeByProvinceAndCity(String province, String city) throws Exception {
        return cityCodeService.getCityCodeByProvinceAndCity(province,city);
    }

    @Override
    public Map<String, Object> updateCityCode() throws Exception {
        return cityCodeService.updateCityCode();
    }
}
