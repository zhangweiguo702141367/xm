package com.zh;

import com.zh.dubbo.manage.qiniu.QNService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceApplicationTests {
	@Autowired
	QNService qnService;
	@Test
	public void contextLoads() {
		Map<String,Object> params = new HashMap<>();
		params.put("bucketname","image");
		params.put("filename","8.png");
		params.put("filepath","D:/8.png");
		try{
			qnService.upload(params);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void downLoad(){
		Map<String,Object> params = new HashMap<>();
		params.put("filename","http://image.lxiaomei.com/8.png");
		try{
			System.out.println(qnService.download(params));
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
