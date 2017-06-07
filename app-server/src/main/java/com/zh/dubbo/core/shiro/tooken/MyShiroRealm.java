package com.zh.dubbo.core.shiro.tooken;

import com.zh.dubbo.entity.UUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**备用参考
 * Created by 70214 on 2017/3/25.
 */
public class MyShiroRealm extends AuthorizingRealm {
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
        UUser user = new UUser();
        user.setPswd("11111");
        user.setNickname("zhangsan");
        return  new SimpleAuthenticationInfo(user, password, getName());
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
