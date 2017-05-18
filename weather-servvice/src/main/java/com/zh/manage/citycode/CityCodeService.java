package com.zh.manage.citycode;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface CityCodeService {
    /**
     * 定时任务更新城市代码(定时每一个月更新一次)
     * @return
     * @throws Exception
     */
    public Map<String,Object> updateCityCode() throws Exception;
    /**
     * 根据省份 城市获取城市代码
     * @param province
     * @param city
     * @return
     * @throws Exception
     */
    public String getCityCodeByProvinceAndCity(String province,String city) throws Exception;
}
