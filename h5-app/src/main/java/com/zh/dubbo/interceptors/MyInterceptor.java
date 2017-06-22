package com.zh.dubbo.interceptors;

import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.token.TokenManage;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.IPUtil;
import com.zh.dubbo.utils.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
public class MyInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(TokenManage.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.out.println(">>>MyInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        String path = httpServletRequest.getServletPath();
        logger.error(path);
        String authtoken = httpServletRequest.getHeader("authtoken");
        String authsign = httpServletRequest.getHeader("authsign");
        //校验authtoken,如果为空判断是否为放行页，是则放行，否则做登录重定向
        if(authtoken == null || authsign == null ||"".equals(authtoken) || "".equals(authsign)){
            if(!path.matches("/")){
                logger.error("here===="+path);
            }else{
                httpServletResponse.sendRedirect("/login");
            }
        }else {
            try {
                if(!path.matches("/")){
                    //如果是不做登录的链接则
                    try{
                        Map<String, Object> signMap = TokenManage.tokenDec(authtoken, authsign);
                        String redisName = signMap.get("redisName").toString();
                        String token = JedisUtil.get(redisName);
                        if ("".equals(token) || !token.equals(authtoken)) {
                            logger.error("token与redis用户登录的token不一致");
                            httpServletResponse.sendRedirect("/login");
                        }
                        String ip = IPUtil.getIpAddr(httpServletRequest);
                        String signIp = signMap.get("clientIP").toString();
                        if (!ip.equals(signIp)) {
                            logger.error("签名ip和请求ip不一致");
                            httpServletResponse.sendRedirect("/login");
                        }
                        Long createTime = Long.valueOf(signMap.get("createTime").toString());
                        Long expireTime = Long.valueOf(signMap.get("expireTime").toString());
                        Long totlaTime = createTime + expireTime;
                        Long nowTime = DateUtil.getCurrentTime();
                        if ((nowTime - totlaTime) < 0) {
                            logger.error("当前签名已过期，用户做登录");
                            httpServletResponse.sendRedirect("/login");
                        }
                        if((nowTime-totlaTime)<=600){
                            //如果剩余token时间不到10分钟则更新token
                            Map<String,Object> newSignMap = TokenManage.tokenUpdate(authtoken,authsign,null);
                            authtoken = newSignMap.get("token").toString();
                            authsign = newSignMap.get("sign").toString();
                        }
                    }catch (Exception e){

                    }
                }else {
                    Map<String, Object> signMap = TokenManage.tokenDec(authtoken, authsign);
                    String redisName = signMap.get("redisName").toString();
                    String token = JedisUtil.get(redisName);
                    if ("".equals(token) || !token.equals(authtoken)) {
                        logger.error("token与redis用户登录的token不一致");
                        httpServletResponse.sendRedirect("/login");
                    }
                    String ip = IPUtil.getIpAddr(httpServletRequest);
                    String signIp = signMap.get("clientIP").toString();
                    if (!ip.equals(signIp)) {
                        logger.error("签名ip和请求ip不一致");
                        httpServletResponse.sendRedirect("/login");
                    }
                    Long createTime = Long.valueOf(signMap.get("createTime").toString());
                    Long expireTime = Long.valueOf(signMap.get("expireTime").toString());
                    Long totlaTime = createTime + expireTime;
                    Long nowTime = DateUtil.getCurrentTime();
                    if (nowTime - totlaTime < 0) {
                        logger.error("当前签名已过期，用户做登录");
                        httpServletResponse.sendRedirect("/login");
                    }
                    if((nowTime-totlaTime)<=600){
                        //如果剩余token时间不到10分钟则更新token
                        Map<String,Object> newSignMap = TokenManage.tokenUpdate(authtoken,authsign,null);
                        authtoken = newSignMap.get("token").toString();
                        authsign = newSignMap.get("sign").toString();
                    }
                    UUser user = (UUser) signMap.get("userInfo");
                    String userId = signMap.get("userId").toString();
                    String loginName = signMap.get("loginName").toString();
                    httpServletRequest.setAttribute("userInfo",user);//用户信息
                    httpServletRequest.setAttribute("userId",userId);//用户id
                    httpServletRequest.setAttribute("loginName",loginName);//用户登录名
                    httpServletRequest.setAttribute("redisName",redisName);
                }
//        Cookie[] cookies = httpServletRequest.getCookies();
//        if (cookies == null || cookies.length == 0) {
//            throw new Exception("illegal Login");
//        }

//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("USER_TOKEN")) {
//                    //User user =
//                    httpServletRequest.setAttribute("USER_TOKEN", "{name: GUHUA, age: 20}");
//                    return true;
//                }
//            }
//            throw new Exception("illegal Login");
            } catch (Exception exp) {
//                throw exp;
                // 异常情况跳转登录页面
                httpServletResponse.sendRedirect("/login");
            }
        }
        httpServletRequest.setAttribute("authtoken",authtoken);
        httpServletRequest.setAttribute("authsign",authsign);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println(">>>MyInterceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
        logger.error("参数项："+o);
        httpServletResponse.addHeader("Set-Cookie", "uid=112; Path=/; HttpOnly");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println(">>>MyInterceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
        httpServletResponse.addHeader("Set-Cookie", "uid=1123; Path=/; HttpOnly");
    }
}
