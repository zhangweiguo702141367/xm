package com.zh.dubbo.manage.xml.impl;

import com.zh.dubbo.dao.WeatherLogDao;
import com.zh.dubbo.entity.WeatherLog;
import com.zh.dubbo.manage.xml.OpenWeatherXmlService;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.xml.XmlUtils;
import com.zh.dubbo.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.*;
/**
 * Created by Administrator on 2017/5/18.
 */
@Service
public class OpenWeatherXmlServiceImpl implements OpenWeatherXmlService {
    @Value("${weather.xml.url}")
    private String waetherXmlUrl;
    @Autowired
    WeatherLogDao weatherLogDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Map<String, Object> getWeatherXmlByCityCode(String citycode,String requestId) throws Exception {
        if(citycode == null || "".equals(citycode)){
            throw new Exception("城市编码获取失败！");
        }
        if(waetherXmlUrl == null || "".equals(waetherXmlUrl)){
            throw new Exception("请求地址获取失败！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(waetherXmlUrl).append("?citykey=").append(citycode);
//        Map<String,Object> params = new HashMap<>();
        WeatherLog weatherLog = new WeatherLog();
        weatherLog.setRequestFunction("getWeatherXmlByCityCode");
        weatherLog.setRequestId(Integer.valueOf(requestId));
        weatherLog.setRequestParam("citykey="+citycode);
        weatherLog.setCreateTime(DateUtil.getCurrentTime());
        try{
        String result = HttpClientUtil.get(sb.toString());
        weatherLog.setResp(result);
        System.out.println("resultcitycode====" + result);
        Element root = XmlUtils.getRootElementFromString(result);
        Element forecast = XmlUtils.getChildElement(root,"forecast");
        List<Element> weathers = XmlUtils.getChildElements(forecast);
        Map<String,Object> response = new HashMap<>();
        // 获取近5日温度情况
        List<Map<String,Object>> weatherList = new LinkedList<>();
        for (Element weather:weathers) {
            Map<String,Object> resultMap = new HashMap<>();
            elementToMap(weather,resultMap,"weather");
            weatherList.add(resultMap);
        }
        // 获取指数建议情况
        Element zhishu = XmlUtils.getChildElement(root,"zhishus");
        List<Element> zhss = XmlUtils.getChildElements(zhishu);
        List<Map<String,Object>> zhishuList = new ArrayList<>();
        for (Element zhs:zhss) {
            Map<String,Object> resultMap = new HashMap<>();
            elementToMap(zhs,resultMap,"zhishu");
            zhishuList.add(resultMap);
        }
        // 获取当前温度
        Element wendu = XmlUtils.getChildElement(root,"wendu");
        String wd = wendu.getTextContent();
        // 获取当前湿度
        Element shidu = XmlUtils.getChildElement(root,"shidu");
        String shd = shidu.getTextContent();
        //获取当前风向
        Element fengxiang = XmlUtils.getChildElement(root,"fengxiang");
        String fx = fengxiang.getTextContent();
        //获取当前风力
        Element fengli = XmlUtils.getChildElement(root,"fengli");
        String fl = fengli.getTextContent();
        Element environment = XmlUtils.getChildElement(root,"environment");
        //获取当前PM25
        Element pm25 = XmlUtils.getChildElement(environment,"pm25");
        String pm = pm25.getTextContent();
        //获取当前污染状况
        Element quality = XmlUtils.getChildElement(environment,"quality");
        String wuran = quality.getTextContent();

        response.put("weathers",weatherList);
        response.put("zhishus",zhishuList);
        response.put("wendu",wd);
        response.put("shidu",shd);
        response.put("fengxiang",fx);
        response.put("fengli",fl);
        response.put("pm25",pm);
        response.put("quality",wuran);
        return response;
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
    public Map<String, Object> getWeatherXmlByCityName(String cityName,String requestId) throws Exception {
        if(cityName == null || "".equals(cityName)){
            throw new Exception("城市地址获取失败！");
        }
        if(waetherXmlUrl == null || "".equals(waetherXmlUrl)){
            throw new Exception("请求地址获取失败！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(waetherXmlUrl).append("?city=").append(cityName);
        WeatherLog weatherLog = new WeatherLog();
        weatherLog.setRequestFunction("getWeatherXmlByCityName");
        weatherLog.setRequestId(Integer.valueOf(requestId));
        weatherLog.setRequestParam("citykey="+cityName);
        weatherLog.setCreateTime(DateUtil.getCurrentTime());
        try{
        String result = HttpClientUtil.get(sb.toString());
        weatherLog.setResp(result);
        System.out.println("resultcityname===="+result);
        Element root = XmlUtils.getRootElementFromString(result);
        Element forecast = XmlUtils.getChildElement(root,"forecast");
        List<Element> weathers = XmlUtils.getChildElements(forecast);
        Map<String,Object> response = new HashMap<>();
        // 获取近5日温度情况
        List<Map<String,Object>> weatherList = new LinkedList<>();
        for (Element weather:weathers) {
            Map<String,Object> resultMap = new HashMap<>();
            elementToMap(weather,resultMap,"weather");
            weatherList.add(resultMap);
        }
        // 获取指数建议情况
        Element zhishu = XmlUtils.getChildElement(root,"zhishus");
        List<Element> zhss = XmlUtils.getChildElements(zhishu);
        List<Map<String,Object>> zhishuList = new ArrayList<>();
        for (Element zhs:zhss) {
             Map<String,Object> resultMap = new HashMap<>();
            elementToMap(zhs,resultMap,"zhishu");
            zhishuList.add(resultMap);
        }
        // 获取当前温度
        Element wendu = XmlUtils.getChildElement(root,"wendu");
        String wd = wendu.getTextContent();
        // 获取当前湿度
        Element shidu = XmlUtils.getChildElement(root,"shidu");
        String shd = shidu.getTextContent();
        //获取当前风向
        Element fengxiang = XmlUtils.getChildElement(root,"fengxiang");
        String fx = fengxiang.getTextContent();
        //获取当前风力
        Element fengli = XmlUtils.getChildElement(root,"fengli");
        String fl = fengli.getTextContent();
        Element environment = XmlUtils.getChildElement(root,"environment");
        //获取当前PM25
        Element pm25 = XmlUtils.getChildElement(environment,"pm25");
        String pm = pm25.getTextContent();
        //获取当前污染状况
        Element quality = XmlUtils.getChildElement(environment,"quality");
        String wuran = quality.getTextContent();

        response.put("weathers",weatherList);
        response.put("zhishus",zhishuList);
        response.put("wendu",wd);
        response.put("shidu",shd);
        response.put("fengxiang",fx);
        response.put("fengli",fl);
        response.put("pm25",pm);
        response.put("quality",wuran);
        return response;
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
    /**
     * @param element
     * @param resultMap
     * @throws Exception
     */
    private void elementToMap(Element element, Map<String,Object> resultMap, String parentName) throws Exception {
        if (element == null) {
            return;
        }
        if ("".equals(parentName)) {
            throw new Exception("父节点名字不能为空！");
        }
        List<Element> elements = com.zh.dubbo.untils.xml.XmlUtils.getChildElements(element);
        if (elements == null || elements.size() == 0) {
            String elementValue = element.getTextContent();
            Node parent = element.getParentNode();
            String elementName = element.getTagName();
            if (!parentName.equals(parent.getNodeName())) {
                elementName = parent.getNodeName() + "_" + element.getTagName();
            }
            resultMap.put(elementName, elementValue);
//            System.out.println("key=="+elementName+"  value=="+elementValue+"parent=="+parent.getNodeName());
            return;
        } else {
            for (Element ele : elements) {
                elementToMap(ele, resultMap, parentName);
            }
        }
    }
}
