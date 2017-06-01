package com.zh.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zh.dubbo.entity.SysPermissionInit;
import com.zh.dubbo.manage.sysPermission.SysPermissonService;
import com.zh.dubbo.service.UserService;
import com.zh.dubbo.untils.DateUtil;
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
    @Override
    public List<SysPermissionInit> getAllSysPermissions() throws Exception{
        return sysPermissonService.getAllSysPermissions();
    }

    @Override
    public int insertUser(Map<String, Object> params) throws Exception {
        return 0;
    }

    @Override
    public void insertLoginLog(Map<String, Object> params) throws Exception {

    }
}
