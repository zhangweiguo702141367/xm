package com.zh.manage.baiduMap.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.dao.CommonDao;
import com.zh.dubbo.untils.DateUtil;
import com.zh.entity.SysBaiDuConfig;
import com.zh.entity.SysBaiduLog;
import com.zh.manage.baiduMap.BaiDuMapService;
import com.zh.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/5/17.
 */
@Service
public class BaiDuMapServiceImpl implements BaiDuMapService {
    @Autowired
    CommonDao commonDao;
    @Override
    public Map<String, Object> getLocationByIP(Map<String, Object> params) throws Exception {
        Map<String,Object> respMap = new HashMap<>();
        respMap.put("resultstatus","1");
        if (params == null || params.size() == 0) {
            throw new Exception("参数列表不能为空");
        }
        if (params.get("member_id") == null || "".equals(params.get("member_id").toString())) {
            throw new Exception("用户信息不能为空");
        }
        Integer member_id = Integer.parseInt(params.get("member_id").toString());
        if (params.get("ip") == null || "".equals(params.get("ip").toString())) {
            throw new Exception("未获取到当前用户IP");
        }
        String ip = params.get("ip").toString();
        String ak = "";
        String bdurl = "";
        try {
            List<SysBaiDuConfig> sysBaiDuConfigList = commonDao.getConfList("map");

            if (sysBaiDuConfigList == null || sysBaiDuConfigList.size() == 0) {
                throw new Exception("获取百度配置失败");
            }
            for (SysBaiDuConfig sysBaiDuConfig : sysBaiDuConfigList) {
                if ("map_ak".equals(sysBaiDuConfig.getNid())) {
                    ak = sysBaiDuConfig.getValue();
                }
                if ("map_bdurl".equals(sysBaiDuConfig.getNid())) {
                    bdurl = sysBaiDuConfig.getValue();
                }
            }
            if ("".equals(ak) || "".equals(bdurl)) {
                throw new Exception("获取百度配置失败");
            }
            Map<String,Object>  data= new HashMap<String,Object>();
            data.put("ip",ip);
            data.put("ak",ak);
            String result = HttpClientUtil.post(bdurl,data);
            Map ss = (Map) JSONObject.parse(result);
            System.out.println("start ===="+JSONObject.toJSONString(ss));
            //Map<String,Object> resultMap =(Map<String,Object>) JSON.parse(result);
            Map content = (Map)ss.get("content");
            Map address_detail = (Map)content.get("address_detail");
            Map point = (Map)content.get("point");
            SysBaiduLog sysBaiduLog = new SysBaiduLog();

            Integer status = Integer.parseInt(ss.get("status").toString());
            if(status == 0){
                respMap.put("province",address_detail.get("province").toString());
                respMap.put("city",address_detail.get("city").toString());
                // resultstatus为0则表示调用接口成功 1表示调用接口失败
                respMap.put("resultstatus","0");
                try {
                    sysBaiduLog.setIp(ip);
                    sysBaiduLog.setResp(JSONObject.toJSONString(ss));
                    sysBaiduLog.setProvince(address_detail.get("province").toString());
                    sysBaiduLog.setCity_code(address_detail.get("city_code").toString());
                    sysBaiduLog.setCity(address_detail.get("city").toString());
                    sysBaiduLog.setPoint_x(point.get("x").toString());
                    sysBaiduLog.setPoint_y(point.get("y").toString());
                    sysBaiduLog.setCreateTime(DateUtil.getCurrentTime());
                    sysBaiduLog.setMemberId(member_id);
                    int logId = commonDao.insertBaiDuLog(sysBaiduLog);
                    System.out.println("logId==="+logId);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }finally {
                    System.out.println("百度地址插入数据"+sysBaiduLog.toString());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("获取百度配置失败");
        }
        return respMap;
    }
}
