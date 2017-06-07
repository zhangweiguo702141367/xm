package com.zh.dubbo.manage.auth;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/5.
 */
public interface AuthService {
    /**
     * 手机认证
     * @param params
     * @throws Exception
     */
    public Map<String,Object> phoneAuth(Map<String,Object> params) throws Exception;
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
    public Map<String,Object> emailAuth(Map<String,Object> params) throws Exception;
    /**
     * 判断当前邮箱是否被认证
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
}
