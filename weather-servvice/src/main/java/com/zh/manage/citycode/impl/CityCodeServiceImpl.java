package com.zh.manage.citycode.impl;

import com.zh.dao.CityCodeDao;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.xml.XmlUtils;
import com.zh.entity.CityCode;
import com.zh.manage.citycode.CityCodeService;
import com.zh.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
@Service
public class CityCodeServiceImpl implements CityCodeService{
    @Value("${weather.citycode.url}")
    private String citycodeurl;
    @Autowired
    CityCodeDao cityCodeDao;
    @Override
    public Map<String, Object> updateCityCode() throws Exception {
        if(citycodeurl == null || "".equals(citycodeurl)){
            throw new Exception("请求地址获取失败！");
        }
        System.out.println("请求地址是"+citycodeurl);
        String result = HttpClientUtil.get(citycodeurl);
        System.out.println("resultcityname===="+result);
        Element root = XmlUtils.getRootElementFromString(result);
        Element c = XmlUtils.getChildElement(root,"c");
        List<Element> cityElements = XmlUtils.getChildElements(c);
        List<CityCode> citycodes = new ArrayList<CityCode>();
        elementToList(cityElements,citycodes);
        cityCodeDao.insertCityCodesBatch(citycodes);
        return null;
    }

    @Override
    public String getCityCodeByProvinceAndCity(String province, String city) throws Exception {
        if(province == null || "".equals(province)){
            throw new Exception("省份信息不能为空！");
        }
        if(city == null || "".equals(city)){
            throw new Exception("城市信息不能为空！");
        }
        if(province.endsWith("省") || province.endsWith("市")){
            province = province.substring(0,province.length()-1);
        }
        if(city.endsWith("市")){
            city = city.substring(0,city.length()-1);
        }
        System.out.println("province==="+province+"-----city==="+city);
        CityCode cityCode = cityCodeDao.getCityCodeByProvinceAndCity(city,province);
        if(cityCode == null){
            throw new Exception("获取城市代码异常！");
        }
        return cityCode.getCitycode();
    }

    /**
     *    private String citycode;
     private String cityname;
     private String pinyin;
     private String province;
     private Long createTime;
     * 带attr的xml
     * @throws Exception
     */
    private void elementToList( List<Element>  elements, List<CityCode> citycodes) throws Exception {
        if (elements == null || elements.size() == 0) {
            return;
        }
        Long createTime = DateUtil.getCurrentTime();
        for (Element element:elements) {
            String citycode = element.getAttribute("d1");
            String cityname = element.getAttribute("d2");
            String pinyin = element.getAttribute("d3");
            String province = element.getAttribute("d4");
            if(!citycode.startsWith("101")){
                // 如果不是中国则不保存
                continue;
            }
            CityCode cityCode = new CityCode();
            cityCode.setCitycode(citycode);
            cityCode.setCityname(cityname);
            cityCode.setPinyin(pinyin);
            cityCode.setProvince(province);
            cityCode.setCreateTime(createTime);
            citycodes.add(cityCode);
        }
    }
}
