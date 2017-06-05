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

    /**
     * 手机认证
     * @param params
     * @throws Exception
     */
    public void phoneAuth(Map<String,Object> params) throws Exception;
    /**
     * 手机认证(判断是否手机认证过或者登陆名和当前手机号一致)
     * @param params
     * @throws Exception
     */
    public boolean isPhoneAuth(Map<String,Object> params) throws Exception;
    /**
     * 邮箱认证
     * @param params
     * @throws Exception
     */
    public void emailAuth(Map<String,Object> params) throws Exception;
    /**
     * 实名认证
     * @param params
     * @throws Exception
     */
    public void realNameAuth(Map<String,Object> params) throws Exception;
}
