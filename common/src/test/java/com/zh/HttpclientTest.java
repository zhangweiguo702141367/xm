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
        String postUrl = "http://wecar.qq.com/api/askprice/getprovinceandcity";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ip","223.72.64.105");
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
