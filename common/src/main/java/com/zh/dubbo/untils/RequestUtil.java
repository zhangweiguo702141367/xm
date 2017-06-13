package com.zh.dubbo.untils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 70214 on 2017/4/25.
 */
public class RequestUtil {
    /**
     * 格式化请求参数，去重，去空。
     * post请求方式
     * @param request HttpServletRequest
     * @return 格式化后的结果
     */
    public static Map<String, Object> getRequestMap(HttpServletRequest request) {
        Map<String, Object> target = new HashMap<String,Object>();

        Map<String, String[]> source = request.getParameterMap();
        if (source == null || source.size() == 0)
            return target;

        for (String key : source.keySet()) {
            String[] value = source.get(key);
            if (value != null && value.length > 0) {
                target.put(key, value[0]);
            }
        }

        return target;
    }
    /**
     * get请求方式
     * 获取url参数为map
     */
    public static Map<String, String> getQueryMap(HttpServletRequest request, String charset) throws IOException {
        Map<String, String> queryMap = new HashMap<String, String>();
        String queryString = request.getQueryString();
        String[] params = queryString.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] kv = params[i].split("=");
            if (kv.length == 2) {
                String key = URLDecoder.decode(kv[0], charset);
                String value = URLDecoder.decode(kv[1], charset);
                queryMap.put(key, value);
            } else if (kv.length == 1) { // 参数值为空
                String key = URLDecoder.decode(kv[0], charset);
                queryMap.put(key, "");
            }
        }
        return queryMap;
    }

    /**
     * 获取表单参数为map
     */
    public static Map<String, String> getFormMap(HttpServletRequest request, Map<String, String> queryMap) throws IOException {
        Map<String, String> formMap = new HashMap<String, String>();
        Set<?> keys = request.getParameterMap().keySet();
        for (Object tmp : keys) {
            String key = String.valueOf(tmp);
            if (!queryMap.containsKey(key)) {
                String value = request.getParameter(key);
                if (StringUtils.isEmpty(value)) {
                    formMap.put(key, "");
                } else {
                    formMap.put(key, value);
                }
            }
        }
        return formMap;
    }

}
