package com.zh;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zh.dubbo.manage.auth.AuthService;
import com.zh.dubbo.manage.member.MemberService;
import com.zh.dubbo.manage.spread.SpreadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceApplicationTests {
	@Autowired
	MemberService memberService;
	@Autowired
	SpreadService spreadService;
	@Autowired
	AuthService authService;
	@Test
	public void contextLoads() {
		Map<String,Object> params = new HashMap<>();
		params.put("member_id","12345");
		params.put("login_ip","127.0.0.1");
		try {
			memberService.insertLoginLog(params);
		}catch (Exception e){
			System.out.print(e.getMessage());
		}
	}
	@Test
	public void createUser(){
		Map<String,Object> params = new HashMap<>();
		params.put("user_id","19921011");
		params.put("nick_name","xmmhaha");
		params.put("login_ip","127.0.0.1");
		params.put("mobile_phone","13834412692");
		params.put("password","a123123");
		params.put("login_name","702141367@qq.com");

		try {
			System.out.print(memberService.insertUser(params));
		}catch (Exception e){
			if(e.getMessage().indexOf("userID")>0){
				System.out.println("userId重复======================================");
			}else if(e.getMessage().indexOf("nickName")>0){
				System.out.println("nickName======================================");
			}else if(e.getMessage().indexOf("spreadId")>0){
				System.out.println("spreadId======================================");
			}else if(e.getMessage().indexOf("loginName")>0){
				System.out.println("loginName======================================");
			}else if(e.getMessage().indexOf("phone")>0){
				System.out.println("phone======================================");
			}
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}
	@Test
	public void spread(){
		try{
			Map<String,Object> params = new HashMap<>();
			params.put("member_id",18);
			params.put("nick_name","xm");
			params.put("spread_id","009c8a61f66b40fa9418539799a823ba");
			spreadService.insertSpread(params);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void phoneAuth(){
		try{
			Map<String,Object> params = new HashMap<>();
			params.put("member_id",23);
			params.put("member_phone","13345874785");
			System.out.println("uuser ========="+authService.phoneAuth(params));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void login(){
		try{
			Map<String,Object> params = new HashMap<>();
			params.put("login_name","13834412691");
			params.put("password","a123123");
			System.out.println(memberService.memberLogin(params).toString());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void isEmailAuth(){
		try{
			System.out.println(authService.isEmailAuth("702141367@qq.com"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void emailAuth(){
		try{
			Map<String,Object> params = new HashMap<>();
			params.put("member_id","27");
			params.put("email","2511013760@qq.com");
			System.out.println("==============================================================");
			System.out.println("邮箱认证================="+authService.emailAuth(params));
			System.out.println("==============================================================");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
