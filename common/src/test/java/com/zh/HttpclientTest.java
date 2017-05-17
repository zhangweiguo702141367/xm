package com.zh;

import com.zh.dubbo.untils.HttpClientUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 70214 on 2017/4/24.
 */
public class HttpclientTest {
    @Test
    public void getTest(){
        System.out.println("this is a test for getUrl");
        System.out.println(HttpClientUtil.get("https://daohang.qq.com/?fr=hmpage"));
        System.out.println("this is a test for getUrl");
    }
    @Test
    public void postTest(){
        //        https://api.map.baidu.com/location/ip/?ip=106.39.84.154&output=json&ak=5wim9Xj10uGHkX2as8td5IcalCyGdmb2&sn=f87bddac8043fd85f2d5f069a599361d
        String postUrl = "https://api.map.baidu.com/location/ip";
        //String postUrl = "http://wecar.qq.com/api/askprice/getprovinceandcity";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ip","223.72.64.105");
        params.put("ak","5wim9Xj10uGHkX2as8td5IcalCyGdmb2");
        System.out.println("this is a test for postUrl");
        try {
            System.out.println("here====="+HttpClientUtil.post(postUrl,params));
            System.out.println("there ==="+HttpClientUtil.post(postUrl,params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("this is a test for postUrl");
    }

}
