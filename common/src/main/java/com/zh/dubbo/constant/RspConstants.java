package com.zh.dubbo.constant;

/**
 * 返回结果状态定义
 * Created by 70214 on 2017/5/20.
 */
public class RspConstants {
    //成功标志
    public final static int SUCCESS = 200;
    //获取配置信息异常
    public final static int CONFIGERROR = 201;
    //请求参数异常
    public final static int REQUESTERROR = 202;
    //http请求异常
    public final static int HTTPERROR = 203;
    //数据库异常
    public final static int SQLERROR = 204;
    //其他异常
    public final static int OTHERERROR = 205;
    //未登录
    public final static int UNLOGIN =  206;
    //未授权
    public final static int UNAUTHOR = 207;
    //业务异常
    public final static int SERVICEERROR = 208;
    //需要用户确认的
    public final static int CONFIRM = 209;

}
