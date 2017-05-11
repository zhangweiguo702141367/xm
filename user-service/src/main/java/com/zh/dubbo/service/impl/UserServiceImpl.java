package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.entity.SysPermissionInit;
import com.zh.dubbo.manage.sysPermission.SysPermissonService;
import com.zh.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
@Service(protocol = { "dubbo" })
public class UserServiceImpl implements UserService{
    @Autowired
    private SysPermissonService sysPermissonService;
    @Override
    public List<SysPermissionInit> getAllSysPermissions() throws Exception{
        return sysPermissonService.getAllSysPermissions();
    }
}
