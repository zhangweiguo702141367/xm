package com.zh.dubbo.utils;


/**
 * Created by Administrator on 2017/6/13.
 */
public class UniqIdUtil {
    public static String getUserId() throws Exception{
        Long val = JedisUtil.genUniqIdByRedis("userId");
        if (val < 0)
            val = - val;

        val = val % 1000000L;
        return 1+String.format("%06d", val);
    }
    public static String getWeatherRequestId() throws Exception{
        Long val = JedisUtil.genUniqIdByRedis("WeatherRequestId");
        if (val < 0)
            val = - val;

        val = val % 10000000L;
        return 2+String.format("%07d", val);
    }
}
