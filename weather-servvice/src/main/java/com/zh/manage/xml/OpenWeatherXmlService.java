package com.zh.manage.xml;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface OpenWeatherXmlService {
    /**
     * 通过城市编码获取当前天气情况
     * @param citycode
     * @return
     * @throws Exception
     */
    public Map<String,Object> getWeatherXmlByCityCode(String citycode) throws Exception;

    /**
     * 根据城市名字获取当前天气情况
     * @param cityName
     * @return
     * @throws Exception
     */
    public  Map<String,Object> getWeatherXmlByCityName(String cityName) throws Exception;
}
