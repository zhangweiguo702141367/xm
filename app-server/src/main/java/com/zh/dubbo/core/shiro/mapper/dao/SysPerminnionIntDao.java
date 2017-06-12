package com.zh.dubbo.core.shiro.mapper.dao;

import com.zh.dubbo.entity.SysPermissionInit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
@Mapper
@Component
public interface SysPerminnionIntDao {

    @Select("SELECT * FROM zh_system.sys_permission_init")
    List<SysPermissionInit> getAllSysPermissions();
}
