package com.bhjf.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求工具类。
 */
public class RequestUtil {

    /**
     * 格式化请求参数，去重，去空。
     *
     * @param request HttpServletRequest
     * @return 格式化后的结果
     */
    public static Map<String, Object> getRequestMap(HttpServletRequest request) {
        Map<String, Object> target = new HashMap<>();

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
     * 格式化请求参数，去重，去空。
     *
     * @param request HttpServletRequest
     * @return 格式化后的结果
     */
    public static Map<String, Object> getRequestSessionMap(HttpServletRequest request) {
        Map<String, Object> target = new HashMap<>();
        
        Object third_cust_id = request.getSession().getAttribute("third_cust_id");
        Object userType = request.getSession().getAttribute("userType");
        if(third_cust_id != null && userType!= null){
        	target.put("third_cust_id", third_cust_id);
        	target.put("userType", userType);
        }
        return target;
    }
}
