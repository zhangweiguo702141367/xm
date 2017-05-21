package com.zh.dubbo.entity;

import java.io.Serializable;

/**
 * Created by 70214 on 2017/5/20.
 */
public class Config implements Serializable{
    private int id;
    private String nid;
    private String name;
    private String value;
    private String createTime;

    public Config(){
        super();
    }

    public Config(int id, String nid, String name, String value, String createTime) {
        this.id = id;
        this.nid = nid;
        this.name = name;
        this.value = value;
        this.createTime = createTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysBaiDuConfig{" +
                "id=" + id +
                ", nid='" + nid + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
