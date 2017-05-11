package com.zh.dubbo.fo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zh.dubbo.entity.SysPermissionInit;
import com.zh.dubbo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
@Service
public class UserServiceFo {
    @Reference
    UserService userService;
    public List<SysPermissionInit> getAllSysPermissions(){
        try{
            return userService.getAllSysPermissions();
        }catch (Exception e){
            System.out.println(e.getMessage());

            return new ArrayList<SysPermissionInit>();
        }
    }
}
