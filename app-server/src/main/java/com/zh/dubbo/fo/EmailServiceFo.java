package com.zh.dubbo.fo;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.zh.dubbo.core.shiro.mapper.manage.auth.AuthService;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.service.EmailService;
import com.zh.dubbo.untils.Base64;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.RequestUtil;
import com.zh.dubbo.untils.security.RsaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14.
 */
@Service
public class EmailServiceFo {
    @Value("${email.expire.time}")
    private String expire_time;
    @Reference(version = "1.0.1")
    EmailService emailService;
    @Autowired
    AuthService authService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public boolean isEmailAuth(String email) throws Exception{
        return authService.isEmailAuth(email);
    }
    /**
     * 邮箱认证
     * @param params
     * @return
     * @throws Exception
     */
    public UUser emailAuth(Map<String, Object> params) throws Exception{

        return authService.emailAuth(params);
    }

    /**
     * 发送认证邮箱
     * @param params
     * @throws Exception
     */
    public void sendAuthEmail(Map<String,Object> params) throws Exception{
        emailService.emailSingleSend(params);
    }

    /**
     * 邮箱认证生成token和签名
     * @param member_id
     * @param login_name
     * @return
     * @throws Exception
     */
    public String getEmailSign(String member_id,String login_name,String email) throws Exception{
        if("".equals(member_id)){
            throw new Exception("用户id不能为空！");
        }
        if("".equals(login_name)){
            throw new Exception("登录名不能为空！");
        }
        if("".equals(email)){
            throw new Exception("认证邮箱不能为空！");
        }
        String privateRsa = emailService.getPrivateRsa();
        Map<String,Object> signMap = new HashMap<>();
        signMap.put("member_id",member_id);
        signMap.put("email",email);
        signMap.put("login_name",login_name);
        signMap.put("expire_time",expire_time);
        signMap.put("create_time", DateUtil.getCurrentTime());
        String signJson = JSON.json(signMap);
        byte[] encodedData = RsaUtil.encryptByPrivateKey(signJson.getBytes(), privateRsa);
        logger.error("加密后" + Base64.encodeToString(encodedData,true));
        String sign = RsaUtil.sign(encodedData, privateRsa);
        String encodedStr = Base64.encodeToString(encodedData,true);
        StringBuffer sb = new StringBuffer();
        sb = sb.append("?token=").append(encodedStr).append("&sign=").append(sign);
        return sb.toString();
    }

    /**
     * 根据token和sign解密邮箱认证token并获取member_id和用户登录名
     * @param token
     * @param sign
     * @return
     * @throws Exception
     */
    public Map<String,Object> design(String token,String sign) throws Exception{
        if("".equals(token)){
            throw new ProcException("token不能为空");
        }
        if("".equals(sign)){
            throw new ProcException("sign不能为空");
        }
        byte[] encodedData = Base64.decode(token);
        String publicRsa = emailService.getPublicRsa();
        boolean status = RsaUtil.verify(encodedData, publicRsa,sign);
        if(!status){
            throw new ProcException("签名验证错误");
        }
        byte[] decodedData = RsaUtil.decryptByPublicKey(encodedData, publicRsa);
        String resultString = new String(decodedData);
        Map<String,Object> signMap = JSON.parse(resultString,Map.class);
        String[] params = new String[]{"member_id","login_name","expire_time","create_time","email"};
        if(!RequestUtil.hasAllKey(signMap,params)){
            throw new ProcException("token不合法");
        }
        Long current_time = DateUtil.getCurrentTime();
        Long create_time = Long.valueOf(signMap.get("create_time").toString());
        Long expire_time = Long.valueOf(signMap.get("expire_time").toString());
        if((expire_time+create_time)<=current_time){
            throw new ProcException("token已失效，请重新获取邮件");
        }
        return signMap;
    }
}
