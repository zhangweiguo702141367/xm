package com.bhjf.util;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.Security;


/**
 * 加密工具类
 */
public class SecurityUtil {

    private static Logger logger = Logger.getLogger(SecurityUtil.class);

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
//            Provider[] ps = Security.getProviders();
//            for (int i = 0 ; i < ps.length ; i ++) {
//                try {
//                    KeyFactory keyf 				= KeyFactory.getInstance("RSA", ps[i].getName());
//                    System.out.println("Provider [" + ps[i].getName() + "] has RSA");
//                } catch (Exception e) {
//                    System.out.println("Provider [" + ps[i].getName() + "] no RSA");
//                }
//            }
//
//            for (int i = 0 ; i < ps.length ; i ++) {
//                try {
//                    KeyFactory keyf 				= KeyFactory.getInstance("SHA1WithRSA", ps[i].getName());
//                    System.out.println("Provider [" + ps[i].getName() + "] has SHA1WithRSA");
//                } catch (Exception e) {
//                    System.out.println("Provider [" + ps[i].getName() + "] no SHA1WithRSA");
//                }
//            }
        } catch (Exception e) {
            logger.error("load BouncyCastleProvider failed: " + e.getMessage());
        }
    }


    /**
     * 生成随机数
     *
     * @return int 随机数
     */
    public static int rand() {
        SecureRandom sr = new SecureRandom();
        return sr.nextInt();
    }

    /**
     * BASE64 编码
     *
     * @param data 原始数据
     * @return String 编码后的字符串
     * @throws Exception 字符串转换时可能引发的异常
     */
    public static String encode(byte[] data) throws Exception {
        return new String(Base64.encode(data));
    }

    /**
     * BASE64 解码
     *
     * @param text BASE64编码的数据
     * @return byte[] 解码后的数据
     * @throws Exception 进行BASE64解码时可能产生异常
     */
    public static byte[] decode(String text) throws Exception {
        return Base64.decode(text);
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

    /**
     * MD5 摘要算法
     *
     * @param data 原始数据
     * @return byte[] 摘要数据
     * @throws Exception 可能抛出参数无效或算法相关的异常
     */
    public static byte[] md5(byte[] data) throws Exception {
        MD5Digest md5 = new MD5Digest();

        int outLen = md5.getDigestSize();
        byte[] out = new byte[outLen];

        md5.update(data, 0, data.length);
        md5.doFinal(out, 0);

        return out;
    }

    /**
     * SHA1 摘要算法
     *
     * @param data 原始数据
     * @return byte[] 摘要数据
     * @throws Exception 可能抛出参数无效或算法相关的异常
     */
    public static byte[] sha1(byte[] data) throws Exception {
        SHA1Digest sha1 = new SHA1Digest();

        int outLen = sha1.getDigestSize();
        byte[] out = new byte[outLen];

        sha1.update(data, 0, data.length);
        sha1.doFinal(out, 0);

        return out;
    }

    /**
     * AES 加密算法
     *
     * @param plainText 明文
     * @param password  密码
     * @return byte[] 密文
     * @throws Exception 数据、算法相关的异常
     */
    public static byte[] aesEncrypt(byte[] plainText, String password) throws Exception {
        byte[] b = md5(password.getBytes());
        SecretKeySpec key = new SecretKeySpec(b, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plainText);
    }

    /**
     * AES 解密算法
     *
     * @param cipherData 密文
     * @param password   密码
     * @return byte[] 明文
     * @throws Exception 数据、算法相关的异常
     */
    public static byte[] aesDecrypt(byte[] cipherData, String password) throws Exception {
        byte[] b = md5(password.getBytes());
        SecretKeySpec key = new SecretKeySpec(b, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherData);
    }

}