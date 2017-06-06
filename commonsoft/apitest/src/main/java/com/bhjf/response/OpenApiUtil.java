package com.bhjf.response;

import com.alibaba.fastjson.JSON;
import com.bhjf.util.RequestUtil;
import com.bhjf.util.RsaUtil;
import com.bhjf.util.SecurityUtil;
import com.bhjf.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 开放式接口类
 * 收到三方请求后处理工具类
 * @author lining
 */
@Repository
public class OpenApiUtil {
    /*********************此段代码可放置数据库中查询然后在(获取配置文件信息)处封装******************************************/
    @Value("${kh.username}")
    private String username;
    @Value("${kh.password}")
    private String password;
    @Value("${kh.privatersa}")
    private String privateRsa;
    @Value("${kh.publicrsa}")
    private String publicRsa;
    @Value("${kh.ip_whitelist}")
    private String ipWhiteList;
    @Value("${kh.status}")
    private String status;
    @Value("${kh.perm_list}")
    private String permList;
    @Value("${kh.client_nid}")
    private String clientNid;
    @Value("${kh.client_name}")
    private String clientName;
    /************************************************************************************/
    protected static Logger logger = Logger.getLogger(OpenApiUtil.class);

    private static final int CODE_BASE = 200;

    public final int CODE_SUCCESS = CODE_BASE;
    public final int CODE_UNSUPPORTED_METHOD = CODE_BASE + 1;
    public final int CODE_INVALID_CLIENT_NID = CODE_BASE + 2;
    public final int CODE_OUT_OF_SERVICE = CODE_BASE + 3;
    public final int CODE_NOT_IN_IP_WHITELIST = CODE_BASE + 4;
    public final int CODE_NO_PERMITTED = CODE_BASE + 5;
    public final int CODE_INVALID_KEY = CODE_BASE + 6;
    public final int CODE_INVALID_DATA = CODE_BASE + 7;
    public final int CODE_DECRYPT_FAILURE = CODE_BASE + 8;
    public final int CODE_ENCRYPT_FAILURE = CODE_BASE + 9;
    public final int CODE_AUTHENTICATION_FAILURE = CODE_BASE + 10;
    public final int CODE_INTERNAL_ERROR = CODE_BASE + 11;
    public final int CODE_INVALID_PARAMETER = CODE_BASE + 12;
    public final int CODE_PROCESS_FAILURE = CODE_BASE + 13;
    public final int CODE_UNKNOWN = CODE_BASE + 14;

    private static String[] codeDescriptionList = new String[]{
            // 封装类错误
            "success",
            "unsupported method",
            "invalid client nid",
            "out of service",
            "not in ip whitelist",
            "no permitted",
            "invalid key",
            "invalid data",
            "decrypt failure",
            "encrypt failure",
            "authentication failure",
            "internal error",

            // 应用专属错误
            "invalid parameter",
            "process failure",

            // 封装类错误
            "unknown error"
    };

    private static String[] supportedMethodList = new String[]{
            "smoke",
            "ifExistWalletAccount"
    };


    public class Context {

        public String clientId; // 客户号
        public String clientName; // 客户名
        public String method;
        public String sn; // 流水号
        private Integer half; // 单工还是双工
        private String nid; // 用户简称
        public String request; // 全部请求报文
        public String response; // 全部应答报文
        public String req; // 可读请求报文
        public String res; // 可读应答报文
        public int status; // 状态

        public Context() {
            this.status = -1;
        }

        public void setRequest(String method, String clientIp, String clientNid, Integer half, String key, String data) {
            Map<String, Object> requestMap = new HashMap<String, Object>();
            requestMap.put("method", method);
            requestMap.put("clientIp", clientIp);
            requestMap.put("clientNid", clientNid);
            requestMap.put("key", key);
            requestMap.put("data", data);
            requestMap.put("half", half);

            this.method = method;
            this.request = requestMap.toString();
            this.half = half;
            this.nid = clientNid;
        }

        public void setClient(String id, String name) {
            clientId = id;
            clientName = name;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setReq(Map<String, Object> map) {
            this.req = map.toString();
        }

        public void setResponse(Map<String, Object> map) {
            this.response = map.toString();
        }

        public void setRes(Map<String, Object> map) {
            this.res = map.toString();
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public Integer getHalf() {
            return this.half;
        }

        public String getNid() {
            return this.nid;
        }

    }


    private boolean isMethodSupported(String method) {
        if (method == null || method.length() == 0)
            return false;

        for (int i = 0; i < supportedMethodList.length; i++) {
            if (supportedMethodList[i].equals(method)) {
                return true;
            }
        }

        return false;
    }

    private String getCodeDescription(int code) {
        if (code < CODE_BASE || code > CODE_UNKNOWN)
            code = CODE_UNKNOWN;

        return codeDescriptionList[code - CODE_BASE];
    }

    private Map<String, Object> setCode(Map<String, Object> map, int code) {
        map.put("code", code);
        map.put("description", getCodeDescription(code));
        map.put("sn", StringUtil.genenrateUniqueInd());
        return map;
    }

    private Map<String, Object> getConfigData(String clientNid) {
        if (clientNid == null || clientNid.length() == 0)
            return null;
        //存储数据库中查询配置  ----------------获取配置文件信息------------------------------------
        Map<String,Object> conf = new HashMap<>();
        conf.put("id",clientNid);
        conf.put("client_name",clientName);
        conf.put("username",username);
        conf.put("password",password);
        conf.put("rsa_public",publicRsa);
        conf.put("rsa_private",privateRsa);
        conf.put("ip_whitelist",ipWhiteList);
        conf.put("perm_list",permList);
        conf.put("status",status);
        return conf;
    }

    private boolean inIpWhitelist(Map<String, Object> configMap, String clientIp) {
        if (configMap.get("ip_whitelist") == null)
            return true;

        String ipWhiteListStr = configMap.get("ip_whitelist").toString().trim();
        if (ipWhiteListStr.length() == 0)
            return true;

        String[] ipWhiteList = ipWhiteListStr.split(",");
        if (ipWhiteList == null || ipWhiteList.length == 0)
            return true;

        for (int i = 0; i < ipWhiteList.length; i++) {
            if (ipWhiteList[i].equals(clientIp))
                return true;
        }

        return false;
    }

    private boolean isPermitted(Map<String, Object> configMap, String method) {
        if (configMap.get("perm_list") == null)
            return false;

        String permListStr = configMap.get("perm_list").toString().trim();

        String[] permList = permListStr.split(",");
        if (permList == null || permList.length == 0)
            return false;

        for (int i = 0; i < permList.length; i++) {
            if (permList[i].equals(method))
                return true;
        }

        return false;
    }

    private Map<String, Object> decryptData(Map<String, Object> configMap, String key, String data) {
        try {
            String privateKey = configMap.get("rsa_private").toString();
            byte[] plainKey = RsaUtil.decryptWithPrivateKey(SecurityUtil.decode(key), privateKey);
            String plainText = new String(SecurityUtil.aesDecrypt(SecurityUtil.decode(data), new String(plainKey)), "UTF-8");
            return JSON.parseObject(plainText, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean verifyData(Map<String, Object> configMap, Map<String, Object> data) {
        if (data.get("username") == null || !data.get("username").toString().equals(configMap.get("username").toString()))
            return false;

        if (data.get("password") == null || !data.get("password").toString().equals(configMap.get("password").toString()))
            return false;

        return true;
    }

    private String encryptKey(Integer half, Map<String, Object> configMap, String plainKey) {

        try {
            if (half != null && half == 1) {
                String publicKey = configMap.get("rsa_public").toString();
                byte[] cipherKey = RsaUtil.encryptWithPublicKey(plainKey.getBytes(), publicKey);
                return SecurityUtil.encode(cipherKey);
            } else {
                String privateKey = configMap.get("rsa_private").toString();
                byte[] cipherKey = RsaUtil.encryptWithPrivateKey(plainKey.getBytes(), privateKey);
                return SecurityUtil.encode(cipherKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成上下文
     *
     * @return Context 上下文
     */
    public Context newContext() {
        return new Context();
    }

    /**
     * 获取请求数据
     *
     * @param method    请求方法
     * @param clientIp  客户端IP
     * @param clientNid 客户NID
     * @param half      单工还是双工
     * @param key       加密后的密钥
     * @param data      加密后的数据
     * @return 解析后的请求结果
     */
    public Map<String, Object> getRequestData(Context ctx, String method, String clientIp, String clientNid, Integer half, String key, String data) {
        ctx.setRequest(method, clientIp, clientNid, half, key, data);

        Map<String, Object> map = new HashMap<>();
        map.put("method", method);

//        if (!isMethodSupported(method))
//            return setCode(map, CODE_UNSUPPORTED_METHOD);

        if (key == null || key.length() == 0)
            return setCode(map, CODE_INVALID_KEY);

        if (data == null || data.length() == 0)
            return setCode(map, CODE_INVALID_DATA);

        Map<String, Object> configMap = getConfigData(clientNid);
        if (configMap == null || configMap.size() == 0)
            return setCode(map, CODE_INVALID_CLIENT_NID);

        ctx.setClient(configMap.get("id").toString(), configMap.get("client_name").toString());

        try {
            if (!configMap.get("status").toString().equals("1"))
                return setCode(map, CODE_OUT_OF_SERVICE);

            if (!inIpWhitelist(configMap, clientIp))
                return setCode(map, CODE_NOT_IN_IP_WHITELIST);

//            if (!isPermitted(configMap, method))
//                return setCode(map, CODE_NO_PERMITTED);

            Map<String, Object> dataMap = decryptData(configMap, key, data);
            if (dataMap == null || dataMap.size() == 0)
                return setCode(map, CODE_DECRYPT_FAILURE);

            ctx.setReq(dataMap);

            if (!verifyData(configMap, dataMap))
                return setCode(map, CODE_AUTHENTICATION_FAILURE);

            map.put("data", dataMap);
            map.remove("key");
        } catch (Exception e) {
            logger.info("openAPI.getRequestData failed -- " + e.getMessage());
            return setCode(map, CODE_INTERNAL_ERROR);
        }

        return map;
    }

    public Map<String, Object> getRequestParams(Context ctx, String method, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        Map<String, Object> params = RequestUtil.getRequestMap(request);

        try {
            String nid = params.get("nid").toString();
            Integer half = Integer.valueOf(params.get("half").toString());
            String key = params.get("key").toString();
            String data = params.get("data").toString();

            return this.getRequestData(ctx, method, clientIp, nid, half, key, data);
        } catch (Exception e) {
            return setCode(params, CODE_INVALID_PARAMETER);
        }
    }

    /**
     * 判断数据中是否有错误
     *
     * @param map 数据
     * @return boolean
     */
    public boolean hasError(Map<String, Object> map) {
        if (map.get("code") == null)
            return false;

        return (Integer.valueOf(map.get("code").toString())) != CODE_SUCCESS;
    }

    /**
     * 设置应答结果
     *
     * @param code 返回码
     * @param data 应答内容
     * @return String   应答结果
     */
    public Map<String, Object> setResult(Context ctx, int code, Map<String, Object> data) {
        if (data == null) {
            data = new HashMap<>();
        }

        ctx.setStatus((code == CODE_BASE) ? 1 : -1);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nid", ctx.getNid());

        setCode(resultMap, code);

        if (data.size() > 0) {
            try {
                Map<String, Object> configMap = getConfigData(ctx.getNid());
                if (configMap == null || configMap.size() == 0) {
                    setCode(resultMap, CODE_INVALID_CLIENT_NID);
                } else {
                    String key = SecurityUtil.hex(SecurityUtil.md5(("" + SecurityUtil.rand()).getBytes()));

                    String plainData = JSON.toJSONString(data);

                    String cipherData = SecurityUtil.encode(SecurityUtil.aesEncrypt(plainData.getBytes("UTF-8"), key));
                    resultMap.put("data", cipherData);

                    String cipherKey = this.encryptKey(ctx.getHalf(), configMap, key);
                    resultMap.put("key", cipherKey);
                }
            } catch (Exception e) {
                logger.error("openAPI.getResult failed -- " + e.getMessage());
                setCode(resultMap, CODE_INTERNAL_ERROR);
            }
        }

        ctx.setRes(data);
        ctx.setResponse(resultMap);
        ctx.setSn(resultMap.get("sn").toString());

//        this.saveLog(ctx);

        return resultMap;
    }

    /**
     * 获取应答结果
     *
     * @param code 返回码
     * @return String 应答结果
     */
    public Map<String, Object> setResult(Context ctx, int code) {
        return setResult(ctx, code, null);
    }

    public void saveLog(Context ctx) {
        if (ctx.clientId == null)
            return;
        logger.error("日志保存成功！");
    }

}
