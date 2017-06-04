package com.zh.dubbo.enttity;

import java.io.Serializable;

/**
 * Created by 70214 on 2017/6/3.
 */
public class EmailTemplate implements Serializable {
    private int id;
    private String templateNid;
    private String templeteName;
    private String templeteDesc;
    private String templeteValue;
    private int templeteStatus;
    private Long createTime;
    public EmailTemplate(){
        super();
    }

    public EmailTemplate(int id, String templateNid, String templeteName, String templeteDesc, String templeteValue, int templeteStatus, Long createTime) {
        this.id = id;
        this.templateNid = templateNid;
        this.templeteName = templeteName;
        this.templeteDesc = templeteDesc;
        this.templeteValue = templeteValue;
        this.templeteStatus = templeteStatus;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemplateNid() {
        return templateNid;
    }

    public void setTemplateNid(String templateNid) {
        this.templateNid = templateNid;
    }

    public String getTempleteName() {
        return templeteName;
    }

    public void setTempleteName(String templeteName) {
        this.templeteName = templeteName;
    }

    public String getTempleteDesc() {
        return templeteDesc;
    }

    public void setTempleteDesc(String templeteDesc) {
        this.templeteDesc = templeteDesc;
    }

    public String getTempleteValue() {
        return templeteValue;
    }

    public void setTempleteValue(String templeteValue) {
        this.templeteValue = templeteValue;
    }

    public int getTempleteStatus() {
        return templeteStatus;
    }

    public void setTempleteStatus(int templeteStatus) {
        this.templeteStatus = templeteStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
                "id=" + id +
                ", templateNid='" + templateNid + '\'' +
                ", templeteName='" + templeteName + '\'' +
                ", templeteDesc='" + templeteDesc + '\'' +
                ", templeteValue='" + templeteValue + '\'' +
                ", templeteStatus=" + templeteStatus +
                ", createTime=" + createTime +
                '}';
    }
}
