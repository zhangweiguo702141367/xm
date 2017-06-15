package com.zh;

import com.zh.dubbo.manage.citycode.CityCodeService;
import com.zh.dubbo.manage.json.OpenWeatherService;
import com.zh.dubbo.manage.xml.OpenWeatherXmlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceApplicationTests {
	@Autowired
	OpenWeatherService openWeatherService;
	@Autowired
	OpenWeatherXmlService openWeatherXmlService;
	@Autowired
	CityCodeService cityCodeService;
	//json方式
	@Test
	public void contextLoads() {
		try {
//			System.out.println(openWeatherService.getWeatherByCityCode("101010100","12345"));
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
//	//json方式
//    @Test
//	public void getWeatherByCityName(){
//		try{
//			System.out.println(openWeatherService.getWeatherByCityName("北京市","12346"));
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//	}
//	@Test
//	public void getWeatherXmlByCityName(){
//		try{
//			Map result = openWeatherXmlService.getWeatherXmlByCityName("北京市","12347");
////			List<Map<String,Object>> zhishus = (List<Map<String,Object>>) result.get("zhishus");
////			for (Map zhishu:zhishus) {
//				System.out.println(result);
////			}
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//	}
//	@Test
//	public void getWeatherXmlByCityCode(){
//		try{
//			System.out.println(openWeatherXmlService.getWeatherXmlByCityCode("101010100","12348"));
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//	}
//	@Test
//	public void updateCityCode(){
//		try{
//			cityCodeService.updateCityCode();
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//	}
//	@Test
//	public void getCityCode(){
//		try{
//			System.out.println("citycode===="+cityCodeService.getCityCodeByProvinceAndCity("山西","晋中市"));
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//	}
}
