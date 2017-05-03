package com.zh.dubbo.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/3.
 */
public class SmsConfig implements Serializable{
    private int id;
    private String nid;
    private String name;
    private String value;
    public SmsConfig(){
        super();
    }

    public SmsConfig(int id, String nid, String name, String value) {
        this.id = id;
        this.nid = nid;
        this.name = name;
        this.value = value;
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

    @Override
    public String toString() {
        return "SmsConfig{" +
                "id=" + id +
                ", nid='" + nid + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
