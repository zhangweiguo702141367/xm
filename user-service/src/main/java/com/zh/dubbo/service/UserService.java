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
    public Map<String,Object> insertUser(Map<String,Object> params) throws Exception;

    /**
     * 修改用户密码(包含忘记密码和重置密码)
     * @param params
     * @throws Exception
     */
    public void updateMemberPassword(Map<String,Object> params) throws Exception;

    /**
     * 插入用户登录日志
     * @param params
     * @throws Exception
     */
    public void insertLoginLog(Map<String,Object> params) throws Exception;

    /**
     * 手机认证 认证完成之后修改session中存放的用户信息
     * @param params
     * @throws Exception
     */
    public Map<String,Object> phoneAuth(Map<String,Object> params) throws Exception;
    /**
     * 手机认证(判断是否手机认证过或者登陆名和当前手机号一致)
     * 用户认证之前做的校验(或者开户之前)
     * @param params
     * @throws Exception
     */
    public boolean isPhoneAuth(Map<String,Object> params) throws Exception;
    /**
     * 邮箱认证
     * @param params
     * @throws Exception
     */
    public Map<String,Object> emailAuth(Map<String,Object> params) throws Exception;
    /**
     * 邮箱是否被认证
     * 用户认证之前做的校验
     * @param email
     * @throws Exception
     */
    public boolean isEmailAuth(String email) throws Exception;
    /**
     * 实名认证
     * @param params
     * @throws Exception
     */
    public void realNameAuth(Map<String,Object> params) throws Exception;
    /**
     * 用户登录
     * @param params
     * @throws Exception
     */
    public Map<String,Object> memberLogin(Map<String,Object> params) throws Exception;

}
