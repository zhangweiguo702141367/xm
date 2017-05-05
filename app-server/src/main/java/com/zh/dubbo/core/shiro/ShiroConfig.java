package com.zh.dubbo.core.shiro;

import com.zh.dubbo.core.shiro.filters.*;
import com.zh.dubbo.core.shiro.realm.ShiroRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/5.
 */
@Configuration
public class ShiroConfig {
    //登录url
    private String loginUrl;
    //登录成功Url
    private String successUrl;
    //未授权Url
    private String unauthUrl;
    //相隔多久检查一次session的有效性
    private String sessionValidationInterval;
    //session有效期
    private String globalSessionTimeout;
    //rememberMe最大保留天数 MaxAge
    private String maxAge;
    //kickoutUrl 剔除后跳转Url
    private String kickoutUrl;
    //shiro 配置主干
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //设置loginUrl
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        //设置登录成功url
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        //设置未授权登录url
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthUrl);
        //初始设置自定义权限
        Map<String, String> chains = new HashMap<String,String>();
        chains.put("/login", "anon");
        chains.put("/unauthor", "anon");
        chains.put("/logout", "logout");
        chains.put("/base/**", "anon");
        chains.put("/css/**", "anon");
        chains.put("/layer/**", "anon");
        chains.put("/**", "perms");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
        //设置filters
        Map<String, Filter> filters = new HashMap<String,Filter>();
        filters.put("login", loginFilter());
        filters.put("role",roleFilter());
        filters.put("simple",simpleAuthFilter());
        filters.put("permission",permissionFilter());
        filters.put("kickout",ki);
        //暂未设置，稍后设置
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }
    //shiro 安全管理器--------------------------------------------------------------------------------------------------------------//
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(shiroRealm());
        //设置session管理器
        securityManager.setSessionManager(sessionManager());
        //设置记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
        //设置cache管理器
        securityManager.setCacheManager();
    }
    //shiroRealm<授权 认证>---------------------------------------------------------------------------------------------------------//
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }
    //Session管理器----------------------------------------------------------------------------------------------------------------//
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //相隔多久检查一次session的有效性
        sessionManager.setSessionValidationInterval(Long.valueOf(sessionValidationInterval));
        //session 有效时间为半小时 （毫秒单位）
        sessionManager.setGlobalSessionTimeout(Long.valueOf(globalSessionTimeout));
        //sessionDAO
        sessionManager.setSessionDAO();
        //session 监听，可以多个
        List<SessionListener> sessionListeners = new ArrayList<SessionListener>();
//        暂无添加session监听器 稍后添加
//        sessionListeners.add();
        sessionManager.setSessionListeners(sessionListeners);
        //<!-- 间隔多少时间检查，不配置是60分钟 -->
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        //<!-- 是否开启 检测，默认开启 -->
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //是否删除无效的，默认也是开启
        sessionManager.setDeleteInvalidSessions(true);
        //会话Cookie模板
        sessionManager.setSessionIdCookie(sessionIdCookie());

        return sessionManager;
    }
    //SessionDao配置
    //SessionValidationScheduler配置      会话验证调度器
    @Bean
    public ExecutorServiceSessionValidationScheduler sessionValidationScheduler(){
        ExecutorServiceSessionValidationScheduler serviceSessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        serviceSessionValidationScheduler.setSessionManager(sessionManager());

        return sessionValidationScheduler();
    }
    //SessionIdCookie配置
    @Bean
    public SimpleCookie sessionIdCookie(){
        //设置cookie名字
        SimpleCookie sessionidCookie = new SimpleCookie("v_v-s-baidu");
        //设置httpOnly
        sessionidCookie.setHttpOnly(true);
        sessionidCookie.setMaxAge(-1);

        return sessionidCookie;
    }

    //记住我管理器----------------------------------------------------------------------------------------------------------------//
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位）
        rememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        //rememberMeCookie
        rememberMeManager.setCookie(rememberMeCookie());

        return rememberMeManager;
    }
    //SimpleCookie 用于设置rememberCookie
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie remembermeCookie = new SimpleCookie("v_v-re-baidu");
        remembermeCookie.setHttpOnly(true);
        //30天时间，记住我30天
        remembermeCookie.setMaxAge(Integer.valueOf(maxAge));

        return remembermeCookie;
    }
    //各种filter配置---------------------------------------------------------------------------------------------------------------//
    //loginFilter配置
    @Bean
    public LoginFilter loginFilter(){
        LoginFilter loginFilter = new LoginFilter();

        return loginFilter;
    }
    //roleFilter配置
    @Bean
    public RoleFilter roleFilter(){
        RoleFilter roleFilter = new RoleFilter();

        return roleFilter;
    }
    //PermissionFilter配置
    @Bean
    public PermissionFilter permissionFilter(){
        PermissionFilter permissionFilter = new PermissionFilter();

        return permissionFilter;
    }
    //SimpleAuthFilter配置
    @Bean
    public SimpleAuthFilter simpleAuthFilter(){
        SimpleAuthFilter simpleAuthFilter = new SimpleAuthFilter();

        return simpleAuthFilter;
    }
    //kickoutSessionFilter配置
    @Bean
    public KickoutSessionFilter kickoutSessionFilter(){
        KickoutSessionFilter kickoutFilter = new KickoutSessionFilter();
        kickoutFilter.set
    }
    //Shiro生命周期处理器----------------------------------------------------------------------------------------------------------//
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }
}


