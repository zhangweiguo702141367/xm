package com.zh.dubbo.service;

import com.zh.dubbo.entity.SysPermissionInit;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/11.
 */
public interface UserService {
    /**
     * 获取所有初始化权限
     * @return
     */
    public List<SysPermissionInit> getAllSysPermissions() throws Exception;

    /**
     * 创建用户
     * @param params
     * @return
     * @throws Exception
     */
    public int insertUser(Map<String,Object> params) throws Exception;

    /**
     * 插入用户登录日志
     * @param params
     * @throws Exception
     */
    public void insertLoginLog(Map<String,Object> params) throws Exception;


}
