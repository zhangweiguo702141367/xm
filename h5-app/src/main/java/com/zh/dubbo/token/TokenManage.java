package com.zh.dubbo.token;

import com.alibaba.fastjson.JSON;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.untils.Base64;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.RequestUtil;
import com.zh.dubbo.untils.security.AESUtil;
import com.zh.dubbo.untils.security.RsaUtil;
import com.zh.dubbo.utils.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
public class TokenManage {
    private static Logger logger = LoggerFactory.getLogger(TokenManage.class);
    private static String rsa_private = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALr0WBrJ6m++HP0y\n" +
            "MaiJ0hajYnCZUQBfWbzfGAEL7PzYFwJqrmRLDY7K+NbmkaYkEyZf9muuZIlu+yPR\n" +
            "ASv/mz0I1eg0dA4zFkoDWT6PWJrcKcIEruyYBTxMYSVDnrLFMbbGGvXPHay7sQTn\n" +
            "jxwWsTRaPWhGu+3tes2cMLX47ciXAgMBAAECgYBFl+ekMT2/0vlfl2u83XEDVFa0\n" +
            "q53nkZcKI9H/sbzKcnY+RBAe7YFiglQXS0U0n4Exa8+DTivGrTV3o7nX8xKKBgkn\n" +
            "FYNr7q/Qzqyiq8wa6YiyVrriKnsyGu0FN0nwm/IgXbO8R9pn8BsoBkLZjtPezq17\n" +
            "IXbnzt66X2oGJkKfAQJBAPOtdOGhdevkfOIcLrKBjM2oZEow/Kss7XzW7Mzmhkz9\n" +
            "HyugB/SS9DBZ02EPYYwsHxbb71J/n3nUVWJgKEJgb3cCQQDEaJM84sgbQBPp/Egl\n" +
            "N1tl0MhOhZEs4hTn+l+0ztc+CuidgFKuBWnRBvAH6rj4b12ED09pJ26V1SG8mg4G\n" +
            "YPfhAkB16wBel2WbC2yrdnvagfqDIvO1O1Np+knKWa8p0Hw5EVxeHgj7f4mPREiX\n" +
            "9Xt8+3Tn+PIs9/lSWUAgZOvWgxgtAkBfnpHgGIBXU/Dd/2aYR8UFYonCQKCW+1I2\n" +
            "RdyGSBTeLa1l0G1wL05+5yPAlvOKb51Vh6afiQT7iRGtokqlrFvBAkEAxOgUEi3z\n" +
            "YAA4LWWOpa4I5n4wBhHlhDvu3KfvvBruP0DGstLl/CaJDQ64SxuvyfEju15yYazq\n" +
            "0/IMYvpMsugnrQ==";
    private static String rsa_public = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC69Fgayepvvhz9MjGoidIWo2Jw\n" +
            "mVEAX1m83xgBC+z82BcCaq5kSw2OyvjW5pGmJBMmX/ZrrmSJbvsj0QEr/5s9CNXo\n" +
            "NHQOMxZKA1k+j1ia3CnCBK7smAU8TGElQ56yxTG2xhr1zx2su7EE548cFrE0Wj1o\n" +
            "Rrvt7XrNnDC1+O3IlwIDAQAB";

    /**
     * 生成token
     * @param user
     * @param ip
     * @return
     * @throws Exception
     */
    public static Map<String,Object> tokenCreate(UUser user,String ip) throws Exception{
        if(user == null){
            throw new Exception("用户信息异常");
        }
        if("".equals(ip)){
            throw new Exception("ip地址不能为空！");
        }
        Map<String,Object> signMap = new HashMap<>();
        signMap.put("clientIP",ip);
        signMap.put("createTime", DateUtil.getCurrentTime());//创建时间
        signMap.put("expireTime",7200);//过期时间为2小时
        signMap.put("userInfo",user);//用户信息
        signMap.put("userId",user.getId());//用户id
        signMap.put("loginName",user.getLoginName());//用户登录名
        signMap.put("redisName","login_"+user.getLoginName());//用户redis存放值格式为toekn.sign
        String signJson = com.alibaba.dubbo.common.json.JSON.json(signMap);
        byte[] encodedData = RsaUtil.encryptByPrivateKey(signJson.getBytes(), rsa_private);
//        logger.error("加密后" + Base64.encodeToString(encodedData,true));
        String sign = RsaUtil.sign(encodedData, rsa_private);
        String token = Base64.encodeToString(encodedData,true);
        StringBuffer sb = new StringBuffer();
        tokenLogin(signMap.get("redisName").toString(),token,7200);//创建完成后做登录
        Map<String,Object> respMap = new HashMap<>();
        respMap.put("token",token);
        respMap.put("sign",sign);
        return respMap;
    }

    /**
     * 解密token
     * @param token
     * @param sign
     * @return
     * @throws Exception
     */
    public static Map<String,Object> tokenDec(String token, String sign) throws Exception{
        if("".equals(token) || "".equals(sign)){
            throw new ProcException("签名验证错误");
        }
        byte[] encodedData = Base64.decode(token);
        boolean status = RsaUtil.verify(encodedData, rsa_public,sign);
        if(!status){
            throw new ProcException("签名验证错误");
        }
        byte[] decodedData = RsaUtil.decryptByPublicKey(encodedData, rsa_public);
        String resultString = new String(decodedData);
        Map<String,Object> signMap = com.alibaba.dubbo.common.json.JSON.parse(resultString,Map.class);
        return signMap;
    }

    /**
     * 更新token
     * @param token
     * @param sign
     * @param user
     * @return
     * @throws Exception
     */
    public static Map<String,Object> tokenUpdate(String token,String sign,UUser user) throws Exception{
        Map<String,Object> signMap = tokenDec(token,sign);
        if(user != null){
            signMap.put("userInfo",user);
        }
        signMap.put("createTime", DateUtil.getCurrentTime());
        String redisName = signMap.get("redisName").toString();
        String signJson = com.alibaba.dubbo.common.json.JSON.json(signMap);
        byte[] encodedData = RsaUtil.encryptByPrivateKey(signJson.getBytes(), rsa_private);
        String newSign = RsaUtil.sign(encodedData, rsa_private);
        String newToken = Base64.encodeToString(encodedData,true);
        StringBuffer sb = new StringBuffer();
        tokenLogin(redisName,newToken,7200);//更新完成后做登录
        Map<String,Object> respMap = new HashMap<>();
        respMap.put("token",newToken);
        respMap.put("sign",newSign);
        return respMap;
    }
    public static void tokenDelete(String redisName) throws Exception{
        if("".equals(redisName)){
            throw new ProcException("登出异常");
        }
        JedisUtil.delByKey(redisName);
    }
    public static void tokenLogin(String redisName,String token,int expireTime) throws Exception {
        if ("".equals(redisName) || "".equals(token)) {
            throw new ProcException("登录异常");
        }
        JedisUtil.setex(redisName, token, expireTime);
    }
    public static String expireAes() throws Exception{
        Long now = DateUtil.getCurrentTime();
        String baseStr = Base64.encodeToString(AESUtil.encrypt(now.toString(),"aa12312"),true);
        String urlEnStr = URLEncoder.encode(baseStr,"UTF-8");
        return urlEnStr;
    }
    public static Long expireDes(String expire) throws Exception{
        String urlDecStr = URLDecoder.decode(expire,"UTF-8");
        Long expireTime = Long.valueOf(new String(AESUtil.decrypt(Base64.decode(urlDecStr),"aa12312")));
        return expireTime;
    }
}
