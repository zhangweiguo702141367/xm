package com.zh.dubbo.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by 70214 on 2017/5/20.
 */

/**
 * 如果status!=200则返回错误信息message 否则返回数据在data
 */
public class RespData implements Serializable{
    private int status;
    private String message;
    private Object data;
    private Long rspTime;

    public RespData(){
        super();
    }

    public RespData(int status, String message, Object data, Long rspTime) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.rspTime = rspTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getRspTime() {
        return rspTime;
    }

    public void setRspTime(Long rspTime) {
        this.rspTime = rspTime;
    }

    @Override
    public String toString() {
        return "RespData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", rspTime=" + rspTime +
                '}';
    }
}
