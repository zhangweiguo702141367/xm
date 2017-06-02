package com.zh;

import com.zh.dubbo.untils.Base64;
import com.zh.dubbo.untils.ParseSystemUtil;
import com.zh.dubbo.untils.StringUtils;
import com.zh.dubbo.untils.security.AESUtil;
import com.zh.dubbo.untils.security.DESCUtil;
import com.zh.dubbo.untils.security.RsaUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/6/2.
 */
public class RSATest {
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT1imS+99x4ZVB538SyuBYrgt3\n" +
            "JE/TREIkRDXESgTk4eYiFhaKxdfmXPXYLSPv5tAFXSYMJeW07EpNiFyvpkjpVdy4\n" +
            "8LmLqUbw4sw2AbldJlwcQbLFnaziPqDO6zhvxCKrBc/C1NEnpNBQs83cjNDSyT8p\n" +
            "n21bo6UscLZf2t68qQIDAQAB";
    String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJPWKZL733HhlUHn\n" +
            "fxLK4FiuC3ckT9NEQiRENcRKBOTh5iIWForF1+Zc9dgtI+/m0AVdJgwl5bTsSk2I\n" +
            "XK+mSOlV3LjwuYupRvDizDYBuV0mXBxBssWdrOI+oM7rOG/EIqsFz8LU0Sek0FCz\n" +
            "zdyM0NLJPymfbVujpSxwtl/a3rypAgMBAAECgYBeOvri6lKhdwU5LcJsiw322HEM\n" +
            "bwPGiNKT5TpUNE3sSiTzAxs5NyQHfKdjNFqctoVPdfEjeyBwctBlnQ5fE2GTCRvD\n" +
            "60kk8DCbSQXkAgPinQDE9IIVpW3NN+sumzUWFjyY7OIjOx0k73x4ZwZm6lp7BteA\n" +
            "TZJ1KrPTJgcfeBwJUQJBAMLkP24zTht6GYmjKxpxCvyNHzMxT8fm0r2cDS7qsnOz\n" +
            "w5o1yw3J++1tygZ7A0mQEotpgxlHjpsG9NELlJfJGM0CQQDCMNr7KrlpgsPJ8ICM\n" +
            "1z+0k6j8cfeDnsk7Vi/tXoRkWjH8vS/WkTY3bBZGFJEHewkTO6yuvLznIGi5m1qF\n" +
            "92NNAkAZzaiMSaSsxKS+8WiSjqb6uyCHYZ8Dg48hZZjlPlO0N/fM4hRpVfXSALg/\n" +
            "1WehBL8/0A94eF3sLqaZSuQ4mHp9AkBKSYRMF2EMswCRTRbwShvNybsYr8R/LoxO\n" +
            "zb7R8Jl3BmYfCIS3cZ+q/gdbUdzVXsTu/aImyn79cBGAB6VOoH/1AkAoDvHdXQUv\n" +
            "uexkfUtlTgYloYdjkkgINTKQi2uqarKTdSXx+FsY2w4gZNVtReN0G9sSYnlAqmWg\n" +
            "PtgqHob1QKZc";
    @Test
    public void rsa() throws Exception{
        /***********************************************************************************/
        /***************************私钥加密***********************************/
        /***************************公钥解密************************************************/
        /***********************************************************************************/

        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + Base64.encodeToString(source.getBytes(),true));
        byte[] data = source.getBytes();
        byte[] encodedData = RsaUtil.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + Base64.encodeToString(encodedData,true));
        byte[] decodedData = RsaUtil.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }
    @Test
    public void testSign() throws Exception {
        /***********************************************************************************/
        /***************************私钥加密***********************************/
        /***************************公钥解密************************************************/
        /***************************私钥签名************************************************/
        /***************************公钥解签************************************************/
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + Base64.encodeToString(source.getBytes(),true));
        byte[] data = source.getBytes();
        byte[] encodedData = RsaUtil.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + Base64.encodeToString(encodedData,true));
        byte[] decodedData = RsaUtil.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RsaUtil.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RsaUtil.verify(encodedData, publicKey,sign);
        System.err.println("验证结果:\r" + status);
    }
    @Test
    public void aes() throws Exception{
        String content = "宋建勇";
        String password = "AD42F6697B035B7580E4FEF93BE20BAD";
        byte[] encryptResult = AESUtil.encrypt(content, password);//加密
        byte[] decryptResult = AESUtil.decrypt(encryptResult,password);//解密
        System.out.println("解密后：" + new String(decryptResult));

        /*容易出错的地方，请看下面代码*/
        System.out.println("***********************************************");
//        try {
//            String encryptResultStr = new String(encryptResult,"utf-8");
//            decryptResult = AESUtil.decrypt(encryptResultStr.getBytes("utf-8"),password);
//            System.out.println("解密后：" + new String(decryptResult));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
        /*这主要是因为加密后的byte数组是不能强制转换成字符串的, 换言之,字符串和byte数组在这种情况下不是互逆的,
         * 要避免这种情况，我们需要做一些修订，可以考虑将二进制数据转换成十六进制表示,
         * 主要有两个方法:将二进制转换成16进制(见方法parseByte2HexStr)或是将16进制转换为二进制(见方法parseHexStr2Byte)*/

        /*然后，我们再修订以上测试代码*/
        System.out.println("***********************************************");
        String encryptResultStr = ParseSystemUtil.parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
        byte[] decryptFrom = ParseSystemUtil.parseHexStr2Byte(encryptResultStr);
        decryptResult = AESUtil.decrypt(decryptFrom,password);//解码
        System.out.println("解密后：" + new String(decryptResult));
    }
    @Test
    public void des() throws Exception{
        byte[] pwd =  DESCUtil.initKey();
        String encryptResultStr = ParseSystemUtil.parseByte2HexStr(pwd);
        System.out.println("encryptResultStr====="+encryptResultStr);
        byte[] enStr = DESCUtil.encrypt("zhangsan".getBytes(),pwd);
        String encryptResultebStr = ParseSystemUtil.parseByte2HexStr(enStr);
        System.out.println("encryptResultebStr==="+encryptResultebStr);
        byte[] decryptFrom = ParseSystemUtil.parseHexStr2Byte(encryptResultebStr);
        byte[] deStr = DESCUtil.decrypt(decryptFrom,pwd);
        System.out.println("deStr==="+new String(deStr));

    }
}
