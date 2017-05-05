package com.zh.dubbo.core.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/5/5.
 */
@Component("shiroRealm")
public class ShiroRealm extends AuthorizingRealm {

    public ShiroRealm() {
        super();
    }
    /**
     *  认证信息，主要针对用户登录，
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
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
