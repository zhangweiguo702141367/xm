package com.zh;

import com.zh.dubbo.core.shiro.cache.VCache;
import com.zh.dubbo.fo.EmailServiceFo;
import com.zh.dubbo.fo.WeatherServiceFo;
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
	@Autowired
	WeatherServiceFo weatherServiceFo;
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
	@Test
	public void emailPwdDesign(){
		try {
			String token = "TSaretL6FRvkuJgabbndyxBhDjDvuIZtamzVIsp85d3YoP2jyGvszyTjaLKcgojLZID26vQicUzq\n" +
					"1p2w5b3dv97QWowl9cwqPm6/Zgu+6WjDwwuaAFTfZc5W9PlySImFYHXjlyFc2X8PD0+pXzdZupvi\n" +
					"cEkd7HgqE1S70k47TqQ=";
			String sign = "iis8zBt/Vy5KlXoMKlH4XXN6gZ9sLlGFPixl8inQLea1UtyNOvSBwWgH2r3j1PW8SZOAoahIrraZ\n"+
					      "4vDip+w7ZgxglmMlDiLgkqncgYWNZWya2UjgIlqENi4qP/S9CxZK7XbH3uD2StAqQllA8wHt1usp\n"+
					      "vtZ59zUWkvhcZyiUZ3U=";
			System.out.println(emailServiceFo.designEmailPwd(token,sign));
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void getWeather(){
		try{
			System.out.println("当前的天气状况是============\n"+weatherServiceFo.getWeatherInfoByIp("33","106.39.84.154"));
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
