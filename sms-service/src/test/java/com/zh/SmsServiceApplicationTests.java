package com.zh;

import com.zh.dubbo.manage.alibaba.SmsSendAliService;
import com.zh.dubbo.manage.common.CommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsServiceApplicationTests {
	@Autowired
	CommonService commonService;
	@Autowired
	SmsSendAliService smsSendAliService;
	@Test
	public void contextLoads() {
		Map<String,Object> params = new HashMap<>();
		params.put("pageNum",1);
		params.put("pageSize",5);
		try {
			System.out.println(commonService.getSendRecords(params));
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
