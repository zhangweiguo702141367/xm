package com.zh.dubbo.untils;

import org.bouncycastle.util.encoders.Hex;

/**
 * Created by Administrator on 2017/6/2.
 * 进制转换工具类
 */
public class ParseSystemUtil {
    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    /**
     * HEX（16进制） 编码
     *
     * @param data 原始数据
     * @return String 编码后的字符串
     * @throws Exception 进行字符串转换时可能产生异常
     */
    public static String hex(byte[] data) throws Exception {
        return new String(Hex.encode(data));
    }

    /**
     * HEX（16进制） 解码
     *
     * @param text HEX字符串
     * @return byte[] 解码后的数据
     * @throws Exception 进行BASE64解码时或者字符串转换时可能产生异常
     */
    public static byte[] unhex(String text) throws Exception {
        return Hex.decode(text.getBytes());
    }
}
