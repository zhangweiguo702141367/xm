package com.zh.dubbo.manage.sysPermission.impl;

import com.zh.dubbo.dao.SysPerminnionIntDao;
import com.zh.dubbo.entity.SysPermissionInit;
import com.zh.dubbo.manage.sysPermission.SysPermissonService;
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
