package com.zh.dubbo.config;


import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.facade.member.MemberService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证用户登录
 * 
 * @author Administrator
 */
@Component("userRealm")
public class UserRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberService memberService;

	public UserRealm() {
		setName("UserRealm");
		// 采用MD5加密
		setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
	}

	//权限资源角色
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//add Permission Resources
//		info.setStringPermissions(userService.findPermissions(username));----------------------未添加
		//add Roles String[Set<String> roles]
		//info.setRoles(roles);
		return info;
	}
	
	//登录验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		String userName = upt.getUsername();
		String password = new String(upt.getPassword());
		SimpleAuthenticationInfo info = null;
		try {
			UUser user = memberService.memberLogin(userName, password);
			logger.error("登录用户信息=============="+user);
			if(user == null){
				throw new UnknownAccountException();
			}
			info = new SimpleAuthenticationInfo(userName, user.getPassword(), getName());
		}catch (Exception e){
			throw new AuthenticationException(e.getMessage());
		}
		return info;
	}
}