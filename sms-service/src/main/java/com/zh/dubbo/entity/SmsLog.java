package com.zh.dubbo.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/3.
 */
public class SmsLog implements Serializable {
    private int id;
    private String mobile;
    private String msg;
    private int facilitator;
    private String env;
    private Long createTime;
    private int ststus;
    public SmsLog(){
        super();
    }

    public SmsLog(int id, String mobile, String msg, int facilitator, String env, Long createTime, int ststus) {
        this.id = id;
        this.mobile = mobile;
        this.msg = msg;
        this.facilitator = facilitator;
        this.env = env;
        this.createTime = createTime;
        this.ststus = ststus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getFacilitator() {
        return facilitator;
    }

    public void setFacilitator(int facilitator) {
        this.facilitator = facilitator;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getStstus() {
        return ststus;
    }

    public void setStstus(int ststus) {
        this.ststus = ststus;
    }

    @Override
    public String toString() {
        return "SmsLog{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", msg='" + msg + '\'' +
                ", facilitator=" + facilitator +
                ", env='" + env + '\'' +
                ", createTime=" + createTime +
                ", ststus=" + ststus +
                '}';
    }
}
