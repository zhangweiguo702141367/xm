package com.zh.dubbo.core.shiro.tooken;

import com.zh.dubbo.core.shiro.mapper.manage.member.MemberService;
import com.zh.dubbo.entity.UUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**备用参考
 * Created by 70214 on 2017/3/25.
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    MemberService memberService;
    public MyShiroRealm(){
        super();
    }

    /**
     *  认证信息，主要针对用户登录，
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.print("enter doGetAuthorizationInfo");
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        return info;

    }


    /**
     * 授权
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("enter doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        System.out.println("get token");
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        Map<String,Object> params = new HashMap<>();
        params.put("login_name",name);
        params.put("password",password);
        try {
            UUser user = memberService.memberLogin(params);
            return  new SimpleAuthenticationInfo(user, password, getName());
        }catch (Exception e){
            throw new AuthenticationException(e.getMessage());
        }

    }
    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
