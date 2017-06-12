package com.zh.dubbo.core.shiro.mapper.manage.sysPermission.impl;

import com.zh.dubbo.core.shiro.mapper.dao.SysPerminnionIntDao;
import com.zh.dubbo.core.shiro.mapper.manage.sysPermission.SysPermissonService;
import com.zh.dubbo.entity.SysPermissionInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
@Service
public class SysPermissonServiceImpl implements SysPermissonService {
    @Autowired
    SysPerminnionIntDao sysPerminnionIntDao;
    @Override
    public List<SysPermissionInit> getAllSysPermissions() throws Exception{
        return sysPerminnionIntDao.getAllSysPermissions();
    }
}
