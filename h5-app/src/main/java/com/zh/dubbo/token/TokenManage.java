package com.zh.dubbo.token;

import com.alibaba.fastjson.JSON;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.utils.JedisUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
public class TokenManage {
    /**
     * 根据redis存储信息和token存储id去判断用户是否登录
     * @param loginName
     * @param memberId
     * @return
     */
    public static Boolean isLogin(String loginName,String memberId) throws Exception{
        String userInfo =  JedisUtil.get(loginName);
        if("".equals(userInfo)){
            return false;
        }
        UUser user = JSON.parseObject(userInfo,UUser.class);
        Long userId = user.getId();
        Long memberID = Long.valueOf(memberId);
        if(userId != memberID){
            throw new ProcException("用户信息不合法");
        }
        return true;
    }
    public static UUser getUserInfo(String loginName) throws Exception{
        String userInfo =  JedisUtil.get(loginName);
        if("".equals(userInfo)){
            throw new ProcException("用户登录状态异常");
        }
        UUser user = JSON.parseObject(userInfo,UUser.class);

        return user;
    }
    public static String tokenCreate(UUser user) throws Exception{
        if(user == null){
            throw new Exception("用户信息异常");
        }
        Map<String,Object> signMap = new HashMap<>();
        signMap.put("memberId",user.getId());
        signMap.put("createTime", DateUtil.getCurrentTime());
        signMap.put("expireTime",)
    }

}
