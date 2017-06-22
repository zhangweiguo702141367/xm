package com.zh;

import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.token.TokenManage;
import com.zh.dubbo.untils.Base64;
import com.zh.dubbo.untils.security.AESUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class H5AppApplicationTests {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Test
	public void contextLoads() {
		UUser user = new UUser();
		user.setLoginName("18811328493");
		user.setId(111L);
		String ip = "127.0.0.1";
		try {
			logger.error("token====创建并登录");
			Map<String,Object> map = TokenManage.tokenCreate(user, ip);
			String token = map.get("token").toString();
			String sign = map.get("sign").toString();
			logger.error("----------------------------------------------");
			logger.error("~~~~~~~~~~~~~~~~~~~~~"+TokenManage.tokenDec(token,sign).toString());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
