package com.bhjf.request;

import com.alibaba.fastjson.JSON;
import com.bhjf.util.HttpClientUtil;
import com.bhjf.util.RsaUtil;
import com.bhjf.util.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求数据时数据加解密工具类
 * 开放式接口类
 *
 * @author lining
 */
public class OpenRequestApi {

    private static final int CODE_BASE = 200;

    public final int CODE_SUCCESS = CODE_BASE + 0;

    private boolean devMode = true;             // 连接模式
    private String nid = null;                  // 客户简称
    private String username = null;             // 用户名
    private String password = null;             // 密码
    private String rsaPublic = null;            // RSA 公钥


    private String getServerUrl(String method) {
        if (devMode)
             //return "http://xjuzh4553.imwork.net/wap/openapi/" + method;
             return "http://h5jr.jr.baihe.com:8081/" + method;
        else
            return "http://h5jr.jr.baihe.com:8081/" + method;
    }

    /**
     * 构造函数
     *
     * @param devMode 是否是开发模式，true 是，连接测试环境；false 否，连接生产环境
     * @param nid 用户简称
     * @param username 用户名
     * @param password 密码
     * @param rsaPublic RSA 公钥
     */
    public OpenRequestApi(boolean devMode, String nid, String username, String password, String rsaPublic) {
        this.devMode = devMode;
        this.nid = nid;
        this.username = username;
        this.password = password;
        this.rsaPublic = rsaPublic;
    }

    private Map<String, Object> call(String method, Map<String, Object> args) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("username", username);
        dataMap.put("password", password);

        if (args != null) {
            for (String key : args.keySet()) {
                dataMap.put(key, args.get(key));
            }
        }

        String key = SecurityUtil.hex(SecurityUtil.md5(("" + SecurityUtil.rand()).getBytes()));
//        System.out.println("key = " + key);

        String plainText = JSON.toJSONString(dataMap);

        String cipherData = SecurityUtil.encode(SecurityUtil.aesEncrypt(plainText.getBytes("UTF-8"), key));
        String cipherKey =  SecurityUtil.encode(RsaUtil.encryptWithPublicKey(key.getBytes(), rsaPublic));

        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("nid", nid);
        requestMap.put("key", cipherKey);
        requestMap.put("data", cipherData);
//        System.out.println("requestMap = " + requestMap.toString());
        String serverUrl = getServerUrl(method);
        String response = HttpClientUtil.post(serverUrl, requestMap);

        Map<String, Object> responseMap = JSON.parseObject(response, Map.class);
//        System.out.println("responseMap = " + responseMap.toString());

        if(Integer.valueOf(responseMap.get("code").toString()) != CODE_SUCCESS){
        	  return responseMap;
        }else{
        	 byte[] plainKey = RsaUtil.decryptWithPublicKey(SecurityUtil.decode(responseMap.get("key").toString()), rsaPublic);
             String plainData = new String(SecurityUtil.aesDecrypt(SecurityUtil.decode(responseMap.get("data").toString()), new String(plainKey)), "UTF-8");
             responseMap.put("nid", nid);
             responseMap.put("data", JSON.parseObject(plainData, Map.class));
             responseMap.remove("key");
        }
          
        return responseMap;
    }

    public Map<String, Object> smoke() throws Exception {
        return this.call("smoke", null);
    }
    public Map<String, Object> redeem(String platformId, String memberId,String userNo,String serialNo,String bankCardNo,String amount) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("platformId", platformId);
        dataMap.put("memberId", memberId);
        dataMap.put("userNo", userNo);
        dataMap.put("serialNo", serialNo);
        dataMap.put("bankCardNo", bankCardNo);
        dataMap.put("amount", amount);
        return this.call("redeem", dataMap);
    }
}
