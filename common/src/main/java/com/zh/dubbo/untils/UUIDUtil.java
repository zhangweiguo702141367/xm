package com.zh.dubbo.untils;

import java.util.UUID;

/**
 * Created by 70214 on 2017/6/1.
 */
public class UUIDUtil {
    /**
     * 生成UUID字符串
     * @return
     */
    public static String UUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    /**
     * 生成UUID字符串除去-
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24);
    }

}
