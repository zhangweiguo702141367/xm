package com.bhjf.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpClientUtil {

    public static String post(String path, Map<String, Object> paramMap) throws Exception {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        if(paramMap != null) {
            for(String key : paramMap.keySet()) {
                paramsList.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
            }
        }

        HttpPost httpPost = new HttpPost(path);
        HttpEntity entity = new UrlEncodedFormEntity(paramsList, "UTF-8");
        httpPost.setEntity(entity);

        CloseableHttpClient httpClient = null;
        String result = "";

        try {
            httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            entity = httpResponse.getEntity();
            if(entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }

        return result.trim();
    }

    public static String get(String path) throws Exception {
        CloseableHttpClient httpClient = null;
        String result = "";

        try {
            HttpGet httpGet = new HttpGet(path);
            httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if(entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }

        return result.trim();
    }

}
