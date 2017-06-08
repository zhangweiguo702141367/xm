package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.entity.SysPermissionInit;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.manage.auth.AuthService;
import com.zh.dubbo.manage.member.MemberService;
import com.zh.dubbo.manage.sysPermission.SysPermissonService;
import com.zh.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/11.
 */
@Service(protocol = { "dubbo" })
public class UserServiceImpl implements UserService{
    @Autowired
    private SysPermissonService sysPermissonService;
    @Autowired
    MemberService memberService;
    @Autowired
    AuthService authService;
    @Override
    public List<SysPermissionInit> getAllSysPermissions() throws Exception{
        return sysPermissonService.getAllSysPermissions();
    }

    @Override
    public UUser insertUser(Map<String, Object> params) throws Exception {
        return memberService.insertUser(params);
    }

    @Override
    public void updateMemberPassword(Map<String, Object> params) throws Exception {
        memberService.updateMemberPassword(params);
    }

    @Override
    public void insertLoginLog(Map<String, Object> params) throws Exception {
        memberService.insertLoginLog(params);
    }

    @Override
    public UUser phoneAuth(Map<String, Object> params) throws Exception {
        return authService.phoneAuth(params);
    }

    @Override
    public boolean isPhoneAuth(Map<String, Object> params) throws Exception {
        return authService.isPhoneAuth(params);
    }

    @Override
    public UUser emailAuth(Map<String, Object> params) throws Exception {
        return authService.emailAuth(params);
    }

    @Override
    public boolean isEmailAuth(String email) throws Exception {
        return authService.isEmailAuth(email);
    }

    @Override
    public void realNameAuth(Map<String, Object> params) throws Exception {

    }

    @Override
    public UUser memberLogin(Map<String, Object> params) throws Exception {
        return null;
    }


}
