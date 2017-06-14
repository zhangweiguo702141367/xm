package com.zh;

import com.zh.dubbo.core.shiro.cache.VCache;
import com.zh.dubbo.fo.EmailServiceFo;
import com.zh.dubbo.utils.UniqIdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppServerApplicationTests {
	@Autowired
	EmailServiceFo emailServiceFo;
	@Test
	public void contextLoads() {
		try {
			System.out.println("uuss==="+ UniqIdUtil.getUserId());
		}catch (Exception e){

		}
	}
	@Test
	public void sign(){
		try {
			String str= emailServiceFo.getEmailSign("33", "13834412697","702141367@qq.com");
			System.out.println("str==========="+str);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void design(){
		try {
			String token = "sUl4vjU9/Sbalqy9ubcGmmRm1zqVmKYq1D8qR+3zQ7uW4yy2Yq4GH8/VIsqeKqLEBe+OBvoTwOph\n" +
					"sBYsc/TXcAEMghNmozD6XB+yUVfAF7DTH3habNDH2YTUb3DDSrqHDwV9oy1ZheShwBRixzd+o1xk\n" +
					"LCwo6TD7nugrOv0swkM=";
			String sign = "K+Wnc5jvLd1TP0X8JzyYx1rO2mGEkxtI83PnsTHkfSkLGVIczUuLdjpfrhghYkg0PsaS07nlKsF+\n" +
					"1FALIclrnATnvKSK1aUrpaASD/+XZM5XPZEMDn9sRqLRfTUa+vWx6cxAo266yew664XWjeZsjsOn\n" +
					"jSoC1uXc2e2DFhOhBXE=";
			emailServiceFo.design(token,sign);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
