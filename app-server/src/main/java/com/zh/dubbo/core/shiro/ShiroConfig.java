package com.zh.dubbo.core.shiro;

import com.zh.dubbo.core.shiro.cache.JedisManager;
import com.zh.dubbo.core.shiro.cache.JedisShiroSessionRepository;
import com.zh.dubbo.core.shiro.cache.impl.CustomShiroCacheManager;
import com.zh.dubbo.core.shiro.cache.impl.JedisShiroCacheManager;
import com.zh.dubbo.core.shiro.filters.*;
import com.zh.dubbo.core.shiro.listener.CustomSessionListener;
import com.zh.dubbo.core.shiro.session.CustomSessionManager;
import com.zh.dubbo.core.shiro.tooken.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.Filter;
import java.util.*;

/**
 * Created by Administrator on 2017/5/5.
 */
@Component
@Configuration
public class ShiroConfig {
    //登录url
    @Value("${shiro.loginUrl}")
    private  String loginUrl = "/login";
    //登录成功Url
    @Value("${shiro.successUrl}")
    private  String successUrl = "/index";
    //未授权Url
    @Value("${shiro.unauthUrl}")
    private  String unauthUrl = "/unauth";
    //相隔多久检查一次session的有效性
    @Value("${shiro.sessionValidationInterval}")
    private  String sessionValidationInterval = "2000000";
    //session有效期
    @Value("${shiro.globalSessionTimeout}")
    private  String globalSessionTimeout = "2000000";
    //rememberMe最大保留天数 MaxAge
    @Value("${shiro.maxAge}")
    private  String maxAge = "30";
    //kickoutUrl 剔除后跳转Url
    @Value("${shiro.kickoutUrl}")
    private  String kickoutUrl = "/kickout";
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
        Map<String, String> chains = new LinkedHashMap<String,String>();
        chains.put("/login", "anon");
        chains.put("/unauthor", "anon");
        chains.put("/logout", "logout");
        chains.put("/unauthorized", "anon");
        chains.put("/favicon*", "anon");
//        chains.put("/**", "login,kickout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
        //设置filters
        Map<String, Filter> filters = new LinkedHashMap<String,Filter>();
        filters.put("login", loginFilter());
        filters.put("role",roleFilter());
        filters.put("simple",simpleAuthFilter());
        filters.put("permission",permissionFilter());
        filters.put("kickout",kickoutSessionFilter());
        //
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
        securityManager.setCacheManager(customShiroCacheManager());

        return securityManager;
    }
    //shiroRealm<授权 认证>---------------------------------------------------------------------------------------------------------//
    @Bean
    public MyShiroRealm shiroRealm(){
        MyShiroRealm shiroRealm = new MyShiroRealm();
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
        sessionManager.setSessionDAO(customShiroSessionDAO());
        //session 监听，可以多个
        List<SessionListener> sessionListeners = new ArrayList<SessionListener>();
        //暂无添加session监听器 稍后添加
        sessionListeners.add(customSessionListener());
        //
        sessionManager.setSessionListeners(sessionListeners);
        //<!-- 间隔多少时间检查，不配置是60分钟 -->
//        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        //v2.1
        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
        //<!-- 是否开启 检测，默认开启 -->
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //v2.1
        sessionManager.setSessionIdCookieEnabled(true);
        //是否删除无效的，默认也是开启
        sessionManager.setDeleteInvalidSessions(true);
        //会话Cookie模板
        sessionManager.setSessionIdCookie(sessionIdCookie());

        return sessionManager;
    }
    //SessionDao配置
    @Bean
    public CustomShiroSessionDAO customShiroSessionDAO(){
        CustomShiroSessionDAO customShiroSessionDAO = new CustomShiroSessionDAO();
        customShiroSessionDAO.setShiroSessionRepository(jedisShiroSessionRepository());
        customShiroSessionDAO.setSessionIdGenerator(sessionIdGenerator());

        return customShiroSessionDAO;
    }
   //sessionIdGenerator生成器
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        JavaUuidSessionIdGenerator javaUuidSessionIdGenerator = new JavaUuidSessionIdGenerator();

        return javaUuidSessionIdGenerator;
    }
    //手动管理session
    @Bean
    public CustomSessionManager customSessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setShiroSessionRepository(jedisShiroSessionRepository());
        customSessionManager.setCustomShiroSessionDAO(customShiroSessionDAO());

        return customSessionManager;
    }
    //SessionValidationScheduler配置      会话验证调度器
//    @Bean
//    public ExecutorServiceSessionValidationScheduler sessionValidationScheduler(){
//        ExecutorServiceSessionValidationScheduler serviceSessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
//        serviceSessionValidationScheduler.setSessionManager(sessionManager());
//
//        return sessionValidationScheduler();
//    }
    //v2.1
    @Bean(name = "sessionValidationScheduler")
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(900000);
        return scheduler;
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
    //customSessionListener监听器
    @Bean
    public CustomSessionListener customSessionListener(){
        CustomSessionListener customSessionListener = new CustomSessionListener();
        customSessionListener.setShiroSessionRepository(jedisShiroSessionRepository());

        return customSessionListener;
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
        KickoutSessionFilter.setKickoutUrl(kickoutUrl);

        return kickoutFilter;
    }
    //cacheManage 管理器-----------------------------------------------------------------------------------------------------------//
    @Bean
    public CustomShiroCacheManager customShiroCacheManager(){
        CustomShiroCacheManager customShiroCacheManager = new CustomShiroCacheManager();
        customShiroCacheManager.setShiroCacheManager(jedisShiroCacheManager());

        return customShiroCacheManager;
    }
    //jedisShiroCacheManager
    @Bean
    public JedisShiroCacheManager jedisShiroCacheManager(){
        JedisShiroCacheManager jedisShiroCacheManager = new JedisShiroCacheManager();
        jedisShiroCacheManager.setJedisManager(jedisManager());
        return jedisShiroCacheManager;
    }
    //Shiro生命周期处理器----------------------------------------------------------------------------------------------------------//
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }
    //v2.1
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    //v2.1
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
    //v2.1
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }
    //redis配置------------------------------------------------------------------------------------------------------------------//
    @Bean
    public JedisManager jedisManager(){
        JedisManager jedisManager = new JedisManager();
        jedisManager.setJedisPool(jedisPool());
        return jedisManager;
    }
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }
    @Bean
    public JedisPool jedisPool(){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig(),"47.92.87.170",26379,5000,"aeb03773d5f1ef69bb77925ec2d2f407");
        return jedisPool;
    }
    @Bean
    public JedisShiroSessionRepository jedisShiroSessionRepository(){
        JedisShiroSessionRepository jedisShiroSessionRepository = new JedisShiroSessionRepository();
        jedisShiroSessionRepository.setJedisManager(jedisManager());

        return jedisShiroSessionRepository;
    }
    //静态注入JedisShiroSessionRepository
//    @Bean
//    public MethodInvokingFactoryBean methodInvokingFactoryBean(){
//        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
//        methodInvokingFactoryBean.setStaticMethod("com.zh.dubbo.core.shiro.filters.KickoutSessionFilter.setShiroSessionRepository");
//        List<Object> args = new ArrayList<Object>();
//        args.add(jedisShiroSessionRepository());
//        methodInvokingFactoryBean.setArguments(args);
//
//        return methodInvokingFactoryBean;
//    }
}


