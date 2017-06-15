package com.zh.dubbo.core.shiro.filters;


import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.utils.LoggerUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 开发公司：SOJSON在线工具 <p>
 * 版权所有：© www.sojson.com<p>
 * 博客地址：http://www.sojson.com/blog/  <p>
 * <p>
 * 
 * 判断登录
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　周柏成　2016年6月2日 　<br/>
 *
 * @author zhou-baicheng
 * @email  so@sojson.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public class LoginFilter  extends AccessControlFilter {
	final static Class<LoginFilter> CLASS = LoginFilter.class;
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		System.out.println("enter loginfilter1");
		//临时处理
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		String uri = httpRequest.getRequestURI();//获取URI
		System.out.println(uri);
		if(uri.startsWith("/inner/member")){
			return Boolean.TRUE;
		}
		if(uri.startsWith("/inner/email/e")){
			return Boolean.TRUE;
		}
		UUser token = (UUser) SecurityUtils.getSubject().getPrincipal();
		
		if(null != token || isLoginRequest(request, response)){// && isEnabled()
            return Boolean.TRUE;
        }
		if (ShiroFilterUtils.isAjax(request)) {// ajax请求
			Map<String,String> resultMap = new HashMap<String, String>();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE ;
            
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		System.out.println("enter loginfilter2");
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.TRUE ;
	}
	

}
