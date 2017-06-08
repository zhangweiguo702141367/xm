package com.zh.dubbo.facade.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */
@Service
public class MemberService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Reference
    UserService userService;
    /**
     * 用户登录
     * @param loginName
     * @param password
     * @throws Exception
     */
    public UUser memberLogin(String loginName,String password) throws Exception{
        Map<String,Object> params = Maps.newHashMap();
        params.put("login_name",loginName);
        params.put("password",password);
        return userService.memberLogin(params);
    }
}
