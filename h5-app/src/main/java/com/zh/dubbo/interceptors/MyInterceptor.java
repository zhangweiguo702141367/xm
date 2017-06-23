package com.zh.dubbo.interceptors;

import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.token.TokenManage;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.IPUtil;
import com.zh.dubbo.utils.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
@Order(1)
public class MyInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(TokenManage.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.out.println(">>>MyInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        String path = httpServletRequest.getServletPath();
        logger.error(path);
        String authtoken = httpServletRequest.getHeader("authtoken");
        String authsign = httpServletRequest.getHeader("authsign");
        logger.error("拦截器  authtoken=="+authtoken);
        logger.error("拦截器 authsign=="+authsign);
        //校验authtoken,如果为空判断是否为放行页，是则放行，否则做登录重定向
        if(authtoken == null || authsign == null ||"".equals(authtoken) || "".equals(authsign)){
            if ((path.matches(".*/(inner/member).*"))) {
                logger.error("here===="+path);
                return true;
            }else{
//                httpServletResponse.sendRedirect("/login");
                httpServletResponse.setHeader("nologin","nologin");
                return false;
            }
        }else {
            try {
                String[] token_expire = authtoken.split(".");
                if ((path.matches(".*/(inner/member/v1).*"))) {
                    //如果是不做登录的链接则
                    try{
                        if(token_expire.length != 2){
                            //如果非法token则清除用户登录token
                            httpServletResponse.addHeader("authtoken",null);
                            httpServletResponse.addHeader("authsign",null);
                            return false;
                        }
                        Long last_require = TokenManage.expireDes(token_expire[1]);
                        Long nowTime = DateUtil.getCurrentTime();
                        if(nowTime-last_require>30){
//                            当前autoken已失效
                            httpServletResponse.addHeader("authtoken",null);
                            httpServletResponse.addHeader("authsign",null);
                            return false;
                        }
                        Map<String, Object> signMap = TokenManage.tokenDec(URLDecoder.decode(authtoken,"UTF-8"), URLDecoder.decode(authsign,"UTF-8"));
                        String redisName = signMap.get("redisName").toString();
                        String token = JedisUtil.get(redisName);
                        if ("".equals(token) || !token.equals(authtoken)) {
                            logger.error("token与redis用户登录的token不一致");
//                            httpServletResponse.sendRedirect("/login");
                            httpServletResponse.addHeader("authtoken",null);
                            httpServletResponse.addHeader("authsign",null);
                            return false;
                        }
                        String ip = IPUtil.getIpAddr(httpServletRequest);
                        String signIp = signMap.get("clientIP").toString();
                        if (!ip.equals(signIp)) {
                            logger.error("签名ip和请求ip不一致");
//                            httpServletResponse.sendRedirect("/login");
                            httpServletResponse.addHeader("authtoken",null);
                            httpServletResponse.addHeader("authsign",null);
                            return false;
                        }
                        Long createTime = Long.valueOf(signMap.get("createTime").toString());
                        Long expireTime = Long.valueOf(signMap.get("expireTime").toString());
                        Long totlaTime = createTime + expireTime;

                        if ((nowTime - totlaTime) < 0) {
                            logger.error("当前签名已过期，用户做登录");
                            httpServletResponse.addHeader("authtoken",null);
                            httpServletResponse.addHeader("authsign",null);
                            return false;
//                            httpServletResponse.sendRedirect("/login");
                        }
                        if((nowTime-totlaTime)<=600){
                            //如果剩余token时间不到10分钟则更新token
                            Map<String,Object> newSignMap = TokenManage.tokenUpdate(authtoken,authsign,null);
                            authtoken = newSignMap.get("token").toString();
                            authsign = newSignMap.get("sign").toString();
                            httpServletResponse.addHeader("authtoken",authtoken);
                            httpServletResponse.addHeader("authsign",authsign);
                        }
                    }catch (Exception e){
                        httpServletResponse.addHeader("authtoken",null);
                        httpServletResponse.addHeader("authsign",null);
                    }
                }else {
                    if(token_expire.length != 2){
                        //如果非法token则重定向登录界面
//                        httpServletResponse.sendRedirect("/login");
                        httpServletResponse.setHeader("nologin","nologin");
                        return false;
                    }
                    Long last_require = TokenManage.expireDes(token_expire[1]);
                    Long nowTime = DateUtil.getCurrentTime();
                    if(nowTime-last_require>30){
                        //当前autoken已失效
//                        httpServletResponse.sendRedirect("/login");
                        httpServletResponse.setHeader("nologin","nologin");
                        return false;
                    }
                    Map<String, Object> signMap = TokenManage.tokenDec(URLDecoder.decode(authtoken,"UTF-8"), URLDecoder.decode(authsign,"UTF-8"));
                    String redisName = signMap.get("redisName").toString();
                    String token = JedisUtil.get(redisName);
                    if ("".equals(token) || !token.equals(authtoken)) {
                        logger.error("token与redis用户登录的token不一致");
//                        httpServletResponse.sendRedirect("/login");
                        httpServletResponse.setHeader("nologin","nologin");
                        return false;
                    }
                    String ip = IPUtil.getIpAddr(httpServletRequest);
                    String signIp = signMap.get("clientIP").toString();
                    if (!ip.equals(signIp)) {
                        logger.error("签名ip和请求ip不一致");
//                        httpServletResponse.sendRedirect("/login");
                        httpServletResponse.setHeader("nologin","nologin");
                        return false;
                    }
                    Long createTime = Long.valueOf(signMap.get("createTime").toString());
                    Long expireTime = Long.valueOf(signMap.get("expireTime").toString());
                    Long totlaTime = createTime + expireTime;
                    if (nowTime - totlaTime < 0) {
                        logger.error("当前签名已过期，用户做登录");
//                        httpServletResponse.sendRedirect("/login");
                        httpServletResponse.setHeader("nologin","nologin");
                        return false;
                    }
                    if((nowTime-totlaTime)<=600){
                        //如果剩余token时间不到10分钟则更新token
                        Map<String,Object> newSignMap = TokenManage.tokenUpdate(authtoken,authsign,null);
                        authtoken = newSignMap.get("token").toString();
                        authsign = newSignMap.get("sign").toString();
                        httpServletResponse.addHeader("authtoken",authtoken);
                        httpServletResponse.addHeader("authsign",authsign);
                    }
                    UUser user = (UUser) signMap.get("userInfo");
                    String userId = signMap.get("userId").toString();
                    String loginName = signMap.get("loginName").toString();
                    httpServletRequest.setAttribute("userInfo",user);//用户信息
                    httpServletRequest.setAttribute("userId",userId);//用户id
                    httpServletRequest.setAttribute("loginName",loginName);//用户登录名
                    httpServletRequest.setAttribute("redisName",redisName);
                }
            } catch (Exception exp) {
//                throw exp;
                // 异常情况跳转登录页面
//                httpServletResponse.sendRedirect("/login");
                httpServletResponse.setHeader("nologin","nologin");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println(">>>MyInterceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println(">>>MyInterceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}
