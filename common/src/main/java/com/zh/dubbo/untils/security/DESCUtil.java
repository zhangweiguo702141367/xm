package com.zh.dubbo.untils.security;

/**
 * Created by Administrator on 2017/6/2.
 */
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;

/**
 * DESede算法的别名Triple DES
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 14-5-14
 * Time: 下午10:07
 * To change this template use File | Settings | File Templates.
 */
public class DESCUtil {

    /**
     * 密钥算法
     * Java6支持密钥长度为112位和168位
     */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密解密算法 、工作模式、填充方式
     * Java6支持PKCS5Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static Key tokey(byte[] key) throws Exception {
        //实例化DES密钥材料
        DESedeKeySpec dks = new DESedeKeySpec(key);
        //实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generateSecret(dks);
    }


    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = tokey(key);

        /**
         * 实例化
         * 使用PKCS7Padding填充方式，按如下代码实现
         * Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = tokey(key);

        /**
         * 实例化
         * 使用PKCS7Padding填充方式，按如下代码实现
         * Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,"BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }


    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
        /**
         * 实例化
         * 使用128位或192位长度密钥
         * 按如下代码实现
         *  KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM,"BC");
         */
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        /**
         * 初始化
         * Java6支持密钥长度112位和168位
         * 若使用128位或192为长度密钥，按如下代码实现
         * kg.init(128);
         * 或
         * kg.init(192);
         */
        kg.init(168);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }
}
