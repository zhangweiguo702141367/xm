package com.zh.dubbo.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by 70214 on 2017/5/3.
 */
@Component
public class SmsTemplate implements Serializable {
    private int id;
    private String nid;
    private String desc;
    private String msg;
    private Long createTime;
    private int status;
    private String smsTemplateId;
    public SmsTemplate(){
        super();
    }

    public SmsTemplate(int id, String nid, String desc, String msg, Long createTime, int status, String smsTemplateId) {
        this.id = id;
        this.nid = nid;
        this.desc = desc;
        this.msg = msg;
        this.createTime = createTime;
        this.status = status;
        this.smsTemplateId = smsTemplateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(String smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    @Override
    public String toString() {
        return "SmsTemplate{" +
                "id=" + id +
                ", nid='" + nid + '\'' +
                ", desc='" + desc + '\'' +
                ", msg='" + msg + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", smsTemplateId='" + smsTemplateId + '\'' +
                '}';
    }
}
