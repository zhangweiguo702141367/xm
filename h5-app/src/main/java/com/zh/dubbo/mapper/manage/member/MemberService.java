package com.zh.dubbo.mapper.manage.member;

import com.zh.dubbo.entity.UUser;

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
    public UUser insertUser(Map<String, Object> params) throws Exception;

    /**
     * 插入用户登录日志
     * @param params
     * @throws Exception
     */
    public void insertLoginLog(Map<String, Object> params) throws Exception;

    /**
     * 修改用户密码(包含忘记密码和重置密码)
     * @param params
     * @throws Exception
     */
    public void updateMemberPassword(Map<String, Object> params) throws Exception;
    /**
     * 用户登录
     * @param params
     * @throws Exception
     */
    public UUser memberLogin(Map<String, Object> params) throws Exception;

    /**
     * 根据登录名获取用户信息
     * @param login_name
     * @return
     * @throws Exception
     */
    public UUser getMmeberInfoByLoginName(String login_name) throws Exception;

    /**
     * 根据用户名和用户id获取用户信息
     * @param login_name
     * @param member_id
     * @return
     * @throws Exception
     */
    public UUser getMemberInfoByUsernameAndMemberId(String login_name, String member_id) throws Exception;
    /**
     * 根据用户登录名和绑定手机号判断用户是否合法
     * @param login_name
     * @param mobile_phone
     * @return
     * @throws Exception
     */
    public Boolean isLegalByLoginNameAndMobilePhone(String login_name, String mobile_phone) throws Exception;
    /**
     * 根据用户登录名和邮箱判断用户合法性
     * @param login_name
     * @param email
     * @return
     * @throws Exception
     */
    public Boolean isLegalByLoginNameAndEmail(String login_name, String email) throws Exception;
}
