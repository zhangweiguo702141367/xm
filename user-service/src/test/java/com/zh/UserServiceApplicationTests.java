package com.zh;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zh.dubbo.manage.member.MemberService;
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
		params.put("user_id","19921002");
		params.put("nick_name","bendan");
		params.put("login_ip","127.0.0.1");
		params.put("mobile_phone","18234126877");
		params.put("password","a123123");
		params.put("salt","11");

		try {
			int member_id = memberService.insertUser(params);
			System.out.print(member_id);
		}catch (Exception e){
			System.out.print(e.getMessage());
		}
	}

}
