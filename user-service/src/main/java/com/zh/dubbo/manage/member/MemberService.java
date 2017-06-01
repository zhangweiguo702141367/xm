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
    public int insertUser(Map<String,Object> params) throws Exception;

    /**
     * 插入用户登录日志
     * @param params
     * @throws Exception
     */
    public void insertLoginLog(Map<String,Object> params) throws Exception;
}
