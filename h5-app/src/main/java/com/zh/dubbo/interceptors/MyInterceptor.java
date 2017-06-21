package com.zh.dubbo.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/6/21.
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.out.println(">>>MyInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        String authtoken = httpServletRequest.getHeader("authtoken");
        //校验authtoken是否为空 如果为空跳转未授权页面
        if("".equals(authtoken)){
            httpServletResponse.sendRedirect("/unauthor");
        }
        //解密authtoken是否合法  如果不合法跳转/unauthor 否则设置request中进行下一步操作

//        Cookie[] cookies = httpServletRequest.getCookies();
//        if (cookies == null || cookies.length == 0) {
//            throw new Exception("illegal Login");
//        }
        try {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("USER_TOKEN")) {
//                    //User user =
//                    httpServletRequest.setAttribute("USER_TOKEN", "{name: GUHUA, age: 20}");
//                    return true;
//                }
//            }
//            throw new Exception("illegal Login");
        } catch (Exception exp) {
            throw exp;
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
