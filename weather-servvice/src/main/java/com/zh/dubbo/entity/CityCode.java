package com.zh.dubbo.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/18.
 */
@Component
public class CityCode implements Serializable{
    private int id;
    private String citycode;
    private String cityname;
    private String pinyin;
    private String province;
    private Long createTime;
    public CityCode(){
        super();
    }

    public CityCode(int id, String citycode, String cityname, String pinyin, String province, Long createTime) {
        this.id = id;
        this.citycode = citycode;
        this.cityname = cityname;
        this.pinyin = pinyin;
        this.province = province;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CityCode{" +
                "id=" + id +
                ", citycode='" + citycode + '\'' +
                ", cityname='" + cityname + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", province='" + province + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
