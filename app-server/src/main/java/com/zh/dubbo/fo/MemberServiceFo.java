package com.zh.dubbo.fo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zh.dubbo.core.shiro.mapper.manage.member.MemberService;
import com.zh.dubbo.entity.UUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/12.
 */
@Component
public class MemberServiceFo {
    @Autowired
    MemberService  memberService;

    /**
     * 注册用户
     * @param params
     * @return
     * @throws Exception
     */
    public UUser register(Map<String,Object> params) throws Exception{
        return memberService.insertUser(params);
    }
    /**
     * 忘记或者重置登录密码
     * @param params
     * @throws Exception
     */
    public void updateMemberPassword(Map<String,Object> params) throws Exception{
        memberService.updateMemberPassword(params);
    }
    /**
     * 根据登录名获取用户信息
     * @param login_name
     * @throws Exception
     */
    public UUser getMmeberInfoByLoginName(String login_name) throws Exception{
        return memberService.getMmeberInfoByLoginName(login_name);
    }

    /**
     * 插入用户登录日志
     * @param params
     * @throws Exception
     */
    public void insertLogin(Map<String,Object> params) throws Exception{
        memberService.insertLoginLog(params);
    }

    public UUser phoneLogin(Map<String,Object> params) throws Exception{
        return memberService.memberLogin(params);
    }
    public UUser getMemberInfoByUsernameAndMemberId(String login_name,String member_id) throws Exception{
        return memberService.getMemberInfoByUsernameAndMemberId(login_name,member_id);
    }
}
