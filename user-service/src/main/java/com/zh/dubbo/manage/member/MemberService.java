package com.zh.dubbo.manage.member;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */
public interface MemberService {
    /**
     * 创建用户
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String,Object> insertUser(Map<String,Object> params) throws Exception;

    /**
     * 插入用户登录日志
     * @param params
     * @throws Exception
     */
    public void insertLoginLog(Map<String,Object> params) throws Exception;

    /**
     * 修改用户密码(包含忘记密码和重置密码)
     * @param params
     * @throws Exception
     */
    public void updateMemberPassword(Map<String,Object> params) throws Exception;
    /**
     * 用户登录
     * @param params
     * @throws Exception
     */
    public Map<String,Object> memberLogin(Map<String,Object> params) throws Exception;
}
