package com.zh.dubbo.service;

import com.zh.dubbo.entity.SysPermissionInit;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface UserService {
    /**
     * 获取所有初始化权限
     * @return
     */
    public List<SysPermissionInit> getAllSysPermissions() throws Exception;
}
