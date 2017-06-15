package com.zh;

import com.zh.dubbo.manage.baiduMap.BaiDuMapService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaiduServiceApplicationTests {
	@Autowired
	BaiDuMapService baiDuMapService;
	@Test
	public void contextLoads() {
		Map<String,Object> params = new HashMap<>();
		params.put("member_id","123");
		params.put("ip","223.72.71.218");
		try {
			System.out.println(baiDuMapService.getLocationByIP(params));
		}catch (Exception e){

		}
	}

}
