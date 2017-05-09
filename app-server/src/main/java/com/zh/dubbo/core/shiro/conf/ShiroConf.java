package com.zh.dubbo.core.shiro.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 获取applicattion.propertites里面的配置信息
 * Created by 70214 on 2017/5/9.
 */
@ConfigurationProperties(prefix = "shiro")
@Component
public class ShiroConf implements Serializable{
    //登录url
    private  String loginUrl;
    //登录成功Url
    private  String successUrl;
    //未授权Url
    private  String unauthUrl;
    //相隔多久检查一次session的有效性
    private  String sessionValidationInterval;
    //session有效期
    private  String globalSessionTimeout;
    //rememberMe最大保留天数 MaxAge
    private  String maxAge;
    //kickoutUrl 剔除后跳转Url
    private  String kickoutUrl;

    public ShiroConf(){
        super();
    }

    public ShiroConf(String loginUrl, String successUrl, String unauthUrl, String sessionValidationInterval, String globalSessionTimeout, String maxAge, String kickoutUrl) {
        this.loginUrl = loginUrl;
        this.successUrl = successUrl;
        this.unauthUrl = unauthUrl;
        this.sessionValidationInterval = sessionValidationInterval;
        this.globalSessionTimeout = globalSessionTimeout;
        this.maxAge = maxAge;
        this.kickoutUrl = kickoutUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUnauthUrl() {
        return unauthUrl;
    }

    public void setUnauthUrl(String unauthUrl) {
        this.unauthUrl = unauthUrl;
    }

    public String getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(String sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public String getGlobalSessionTimeout() {
        return globalSessionTimeout;
    }

    public void setGlobalSessionTimeout(String globalSessionTimeout) {
        this.globalSessionTimeout = globalSessionTimeout;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getKickoutUrl() {
        return kickoutUrl;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    @Override
    public String toString() {
        return "ShiroConf{" +
                "loginUrl='" + loginUrl + '\'' +
                ", successUrl='" + successUrl + '\'' +
                ", unauthUrl='" + unauthUrl + '\'' +
                ", sessionValidationInterval='" + sessionValidationInterval + '\'' +
                ", globalSessionTimeout='" + globalSessionTimeout + '\'' +
                ", maxAge='" + maxAge + '\'' +
                ", kickoutUrl='" + kickoutUrl + '\'' +
                '}';
    }
}
