package com.zh.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by 70214 on 2017/5/17.
 */
@Component
public class SysBaiduLog implements Serializable{
    private  int id;
    private String ip;
    private String resp;
    private String province;
    private String city;
    private String city_code;
    private String point_x;
    private String point_y;
    private Long createTime;
    private int memberId;

    public SysBaiduLog (){
        super();
    }

    public SysBaiduLog(int id, String ip, String resp, String province, String city, String city_code, String point_x, String point_y, Long createTime, int memberId) {
        this.id = id;
        this.ip = ip;
        this.resp = resp;
        this.province = province;
        this.city = city;
        this.city_code = city_code;
        this.point_x = point_x;
        this.point_y = point_y;
        this.createTime = createTime;
        this.memberId = memberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getPoint_x() {
        return point_x;
    }

    public void setPoint_x(String point_x) {
        this.point_x = point_x;
    }

    public String getPoint_y() {
        return point_y;
    }

    public void setPoint_y(String point_y) {
        this.point_y = point_y;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "SysBaiduLog{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", resp='" + resp + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", city_code='" + city_code + '\'' +
                ", point_x='" + point_x + '\'' +
                ", point_y='" + point_y + '\'' +
                ", createTime=" + createTime +
                ", memberId=" + memberId +
                '}';
    }
}
