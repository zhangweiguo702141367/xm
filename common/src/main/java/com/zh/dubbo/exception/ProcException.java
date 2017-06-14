package com.zh.dubbo.exception;

/**
 * 业务处理异常，返回给前台提示的
 * Created by Administrator on 2017/6/14.
 */
public class ProcException extends Exception{
    private static final long serialVersionUID = -238091758285157331L;

    private String errCode;
    private String errMsg;

    public ProcException() {
        super();
    }

    public ProcException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcException(String message) {
        super(message);
    }

    public ProcException(Throwable cause) {
        super(cause);
    }

    public ProcException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
