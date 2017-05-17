package com.zh.manage.baiduMap.impl;

import com.alibaba.fastjson.JSONObject;
import com.zh.dao.CommonDao;
import com.zh.entity.SysBaiDuConfig;
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
        if (params == null || params.size() == 0) {
            throw new Exception("参数列表不能为空");
        }
        if (params.get("member_id") == null || "".equals(params.get("member_id").toString())) {
            throw new Exception("用户信息不能为空");
        }
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
//            System.out.println(result);
//            result = convert(result);
            Map ss = (Map)JSONObject.parse(result);
            //Map<String,Object> resultMap =(Map<String,Object>) JSON.parse(result);
            Map content = (Map)ss.get("content");
            Map address_detail = (Map)content.get("address_detail");
            System.out.println(address_detail.get("province"));
//            System.out.println("result====" + result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("获取百度配置失败");
        }
        return null;
    }
    public String convert(String utfstring){
        StringBuffer sb = new StringBuffer();
        int i = -1;
        int pos = 0;
        while((i=utfstring.indexOf("\\u",pos))!=-1){
            sb.append(utfstring.substring(pos,i));
            if(i+5 <utfstring.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(utfstring.substring(i+2,i+6),16));
            }
        }
        return sb.toString();
    }
}
