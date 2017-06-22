package com.zh.dubbo.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */
@Component
public class SysPermissionInit implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键
    private Integer id;
    //链接地址
    private String url;
    //需要具备的权限
    private String permissionInit;
    //排序
    private Integer sort;

    public SysPermissionInit(){
        super();
    }

    public SysPermissionInit(Integer id, String url, String permissionInit, Integer sort) {
        this.id = id;
        this.url = url;
        this.permissionInit = permissionInit;
        this.sort = sort;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissionInit() {
        return permissionInit;
    }

    public void setPermissionInit(String permissionInit) {
        this.permissionInit = permissionInit;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SysPermissionInit{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", permissionInit='" + permissionInit + '\'' +
                ", sort=" + sort +
                '}';
    }
}
