package com.zh.dubbo.aop;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.token.TokenManage;
import com.zh.dubbo.untils.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
/**
 * Created by Administrator on 2017/6/22.
 */
@Aspect
@Component
@Order(99)
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(TokenManage.class);

    @Pointcut("execution(public * com.zh.dubbo.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " +  request.getServletPath());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        Object nologin = response.getHeader("nologin");
        if(nologin != null && "nologin".equals(nologin.toString())){
            ret = RespData.create(RspConstants.UNLOGIN,"未登录",null, DateUtil.getCurrentTime());
            response.setHeader("Access-Control-Expose-Headers","authsign,authtoken");
            response.setHeader("Access-Control-Allow-Origin","*");
        }else{
            Object authtoken = response.getHeader("authtoken");
            Object authsign = response.getHeader("authsign");
            logger.error("aop authtoken==="+authtoken);
            logger.error("aop authsign==="+authsign);
            if(authtoken != null && authsign != null) {
                response.addHeader("authtoken", authtoken+"."+TokenManage.expireAes());
                response.addHeader("authsign", authsign.toString());
                response.setHeader("Access-Control-Expose-Headers","authsign,authtoken");
            }else{
                response.setHeader("Access-Control-Expose-Headers","authsign,authtoken");
            }
            logger.info("RESPONSE : " + ret);
        }
    }


}