package com.zh.dubbo.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 天气请求日志
 * Created by Administrator on 2017/5/22.
 */
@Component
public class WeatherLog implements Serializable{
    private int id;
    private String requestParam;
    private String requestFunction;
    private int requestId;
    private String resp;
    private Long createTime;

    public WeatherLog(){
        super();
    }

    public WeatherLog(int id, String requestParam, String requestFunction, int requestId, String resp, Long createTime) {
        this.id = id;
        this.requestParam = requestParam;
        this.requestFunction = requestFunction;
        this.requestId = requestId;
        this.resp = resp;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getRequestFunction() {
        return requestFunction;
    }

    public void setRequestFunction(String requestFunction) {
        this.requestFunction = requestFunction;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "WeatherLog{" +
                "id=" + id +
                ", requestParam='" + requestParam + '\'' +
                ", requestFunction='" + requestFunction + '\'' +
                ", requestId=" + requestId +
                ", resp='" + resp + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
