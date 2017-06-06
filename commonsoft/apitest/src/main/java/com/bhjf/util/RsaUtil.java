package com.bhjf.util;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA 工具类
 * <p>
 * <p>
 * 1. 提供 BASE64 编解码的能力。<br>
 *
 * @author lining
 */
public class RsaUtil {

    private static Logger logger = Logger.getLogger(SecurityUtil.class);

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            logger.error("load BouncyCastleProvider failed: " + e.getMessage());
        }
    }

    private static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] encodedKey = SecurityUtil.decode(publicKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(SecurityUtil.decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 使用公钥进行数据加密
     *
     * @param plainText 明文
     * @param publicKey 公钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encryptWithPublicKey(byte[] plainText, String publicKey) throws Exception {
        PublicKey pubKey = RsaUtil.getPublicKey(publicKey);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(plainText);
    }

    /**
     * 使用私钥进行数据解密
     *
     * @param cipherData 密文
     * @param privateKey 私钥
     * @return byte[] 明文
     * @throws Exception
     */
    public static byte[] decryptWithPrivateKey(byte[] cipherData, String privateKey) throws Exception {
        PrivateKey priKey = RsaUtil.getPrivateKey(privateKey);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(cipherData);
    }

    /**
     * 使用私钥进行数据加密
     *
     * @param plainText  明文
     * @param privateKey 私钥
     * @return byte[] 密文
     * @throws Exception
     */
    public static byte[] encryptWithPrivateKey(byte[] plainText, String privateKey) throws Exception {
        PrivateKey priKey = RsaUtil.getPrivateKey(privateKey);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        return cipher.doFinal(plainText);
    }

    /**
     * 使用公钥进行数据解密
     *
     * @param cipherData 密文
     * @param publicKey  公钥
     * @return byte[] 明文
     * @throws Exception
     */
    public static byte[] decryptWithPublicKey(byte[] cipherData, String publicKey) throws Exception {
        PublicKey pubKey = RsaUtil.getPublicKey(publicKey);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(cipherData);
    }

}
