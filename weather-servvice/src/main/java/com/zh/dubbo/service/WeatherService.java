package com.zh.dubbo.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface WeatherService {
    /**
     * 通过城市编码获取当前天气情况JSON形式
     * @param citycode
     * @return
     * @throws Exception
     */
    public Map<String,Object> getWeatherByCityCode(String citycode,String requestId) throws Exception;

    /**
     * 根据城市名字获取当前天气情况JSON形式
     * @param cityName
     * @return
     * @throws Exception
     */
    public  Map<String,Object> getWeatherByCityName(String cityName,String requestId) throws Exception;
    /**
     * 通过城市编码获取当前天气情况XML形式
     * @param citycode
     * @return
     * @throws Exception
     */
    public Map<String,Object> getWeatherXmlByCityCode(String citycode,String requestId) throws Exception;

    /**
     * 根据城市名字获取当前天气情况XML形式
     * @param cityName
     * @return
     * @throws Exception
     */
    public  Map<String,Object> getWeatherXmlByCityName(String cityName,String requestId) throws Exception;

    /**
     * 根据省份 城市获取城市代码
     * @param province
     * @param city
     * @return
     * @throws Exception
     */
    public String getCityCodeByProvinceAndCity(String province,String city) throws Exception;
    /**
     * 定时任务更新城市代码(定时每一个月更新一次)
     * @return
     * @throws Exception
     */
    public Map<String,Object> updateCityCode() throws Exception;
}
