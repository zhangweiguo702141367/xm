package com.zh.dubbo.untils.security;

import com.zh.dubbo.untils.Base64;

import java.security.MessageDigest;

/**
 * Created by 70214 on 2017/6/1.
 */
public class SHAUtil {
    /***
     * SHA加密 生成40位SHA码
     * @param inStr
     * @return 返回40位SHA码
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String getPwd(String pwd,String salt,int num) throws Exception {
        if(pwd == null ||"".equals(pwd)){
            throw new Exception("密码不能为空！");
        }
        if(salt == null || "".equals(salt)){
            throw new Exception("加密盐不能为空");
        }
        StringBuffer encrtyStr = new StringBuffer();
        encrtyStr.append(pwd).append(salt);
        String encrtyBase = Base64.encodeToString(encrtyStr.toString().getBytes(),true);
        int i = 0;
        String encrty = encrtyBase;
        for(i=0;i<=num;i++){
            encrty = SHAUtil.shaEncode(encrty);
        }
        return encrty;
    }
}
