package com.zh.dubbo.utils;

import com.zh.dubbo.core.shiro.cache.VCache;

/**
 * Created by Administrator on 2017/6/13.
 */
public class UniqIdUtil {
    public static String getUserId() throws Exception{
        Long val = VCache.genUniqIdByRedis("userId");
        if (val < 0)
            val = - val;

        val = val % 1000000L;
        return 1+String.format("%06d", val);
    }
}
