package com.zh;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zh.dubbo.manage.aliyun.EmailAliyunService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceApplicationTests {
    @Test
    public void contextLoads() {
    }
    @Autowired
	EmailAliyunService emailAliyunService;
//	@Test
//	public void attc(){
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("template_nid","register");
//		params.put("to_email","zhangwg@baihe.com");
//		Map<String,Object> template_param = new HashMap<String,Object>();
//		template_param.put("code","123456");
//		params.put("template_param",template_param);
//		Map<String,Object> attach = new HashMap<>();
//		attach.put("bgfile","D:/1.jpg");
//		attach.put("kuaihui","D:/kuaihui.xls");
//		params.put("attachment",attach);
////		params.put("template_param",template_param);
//		try{
//			emailAliyunService.emailSendAttachment(params);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void test(){
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("template_nid","register");
//		params.put("to_email","zhangwg@baihe.com");
//		Map<String,Object> template_param = new HashMap<String,Object>();
//		template_param.put("code","123456");
//		params.put("template_param",template_param);
//		try{
//			emailAliyunService.emailSingleSend(params);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void sendbatch(){
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("template_nid","register");
//		params.put("to_email","zhangwg@baihe.com;702141367@qq.com");
//		Map<String,Object> template_param = new HashMap<String,Object>();
//		template_param.put("code","123456");
//		params.put("template_param",template_param);
//		try{
//			emailAliyunService.emailBatchSend(params);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void smtphtml(){
//		// 配置发送邮件的环境属性
//		final Properties props = new Properties();
//		// 表示SMTP发送邮件，需要进行身份验证
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.host", "smtpdm.aliyun.com");
////		props.put("mail.smtp.port", ALIDM_SMTP_PORT);
//		// 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.port", "465");
//
//
//		// 发件人的账号
//		props.put("mail.user", "xiaomei@lxiaomei.com");
//		// 访问SMTP服务时需要提供的密码
//		props.put("mail.password", "19921001ZWGzwg");
//		try{
//			// 构建授权信息，用于进行SMTP进行身份验证
//			Authenticator authenticator = new Authenticator() {
//				@Override
//				protected PasswordAuthentication getPasswordAuthentication() {
//					// 用户名、密码
//					String userName = props.getProperty("mail.user");
//					String password = props.getProperty("mail.password");
//					return new PasswordAuthentication(userName, password);
//				}
//			};
//			// 使用环境属性和授权信息，创建邮件会话
//			Session mailSession = Session.getInstance(props, authenticator);
//			// 创建邮件消息
//			MimeMessage message = new MimeMessage(mailSession);
//			// 设置发件人
//			InternetAddress form = new InternetAddress(
//					props.getProperty("mail.user"));
//			message.setFrom(form);
//
//			// 设置收件人
//			InternetAddress to = new InternetAddress("zhangwg@baihe.com");
//			message.setRecipient(MimeMessage.RecipientType.TO, to);
//
//			// 设置邮件标题
//			message.setSubject("猪");
////			// 设置邮件的内容体
////			message.setContent("谢谢你给我动力", "text/html;charset=UTF-8");
//			/******************************************************************************************************/
//			//邮件正文
//			MimeMultipart multipart = new MimeMultipart("mixed");
//			message.setContent(multipart);
//			 /*
//         * 创建邮件的内容
//         * 包括一个邮件正文和两个附件
//         */
//			MimeBodyPart content = new MimeBodyPart();      //邮件内容
//			MimeBodyPart attch1 = new MimeBodyPart();      //附件1
//			MimeBodyPart attch2 = new MimeBodyPart();      //附件2
//			//将邮件内容添加到multipart中
//			multipart.addBodyPart(content);
//			multipart.addBodyPart(attch1);
//			multipart.addBodyPart(attch2);
//
//			//设置附件1
//			DataSource ds1 = new FileDataSource("D:\\springboot\\springboot.iml");
//			DataHandler dh1 = new DataHandler(ds1);
//			attch1.setDataHandler(dh1);
//			attch1.setFileName("springboot.iml");
//			//设置附件2
//			DataSource ds2 = new FileDataSource("D:\\springboot\\springboot.zip");
//			DataHandler dh2 = new DataHandler(ds2);
//			attch2.setDataHandler(dh2);
//			attch2.setFileName(MimeUtility.encodeText("springboot.zip"));
//        /*
//         * 设置内容（正文）---是一个复杂体
//         * 包括HTML正文和显示一张图片
//         */
//			MimeMultipart bodyMultipart = new MimeMultipart("related");
//			content.setContent(bodyMultipart);
//			//构造正文
//			MimeBodyPart htmlBody = new MimeBodyPart();
//			MimeBodyPart gifBody = new MimeBodyPart();
//			bodyMultipart.addBodyPart(htmlBody);
//			bodyMultipart.addBodyPart(gifBody);
//
//			//设置图片
//			DataSource gifds = new FileDataSource("D:/8.png");
//			DataHandler gifdh = new DataHandler(gifds);
//			gifBody.setDataHandler(gifdh);
//			gifBody.setHeader("Content-ID", "<"+gifds.getName()+">");
////			gifBody.setFileName("8.png");
//			//gifBody.setHeader("Content-Location", "http://www.itcast.cn/logo.gif");
//			//设置HTML正文
//
//			htmlBody.setContent("<div style=\'background-image:url(cid:8.png);height:100%;width:100%;color:green; background-repeat:repeat; background-position:100% 100%; background-attachment:fixed; text-indent:em; \'>这是我的第三个JavaMail测试哦!包括了附件和图片，有点儿复杂...</div><br>" +
//					"", "text/html;charset=UTF-8");
//
//
//			message.saveChanges();        //生成邮件
//			/******************************************************************************************************/
//			// 发送邮件
//			Transport.send(message);
//		}catch (Exception e){
//			e.printStackTrace();
//			System.out.println("RESULT=================="+e.getMessage());
//		}
//	}
//	@Test
//	public void html(){
//		// 配置发送邮件的环境属性
//		final Properties props = new Properties();
//		// 表示SMTP发送邮件，需要进行身份验证
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.host", "smtpdm.aliyun.com");
////		props.put("mail.smtp.port", ALIDM_SMTP_PORT);
//		// 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.port", "465");
//
//
//		// 发件人的账号
//		props.put("mail.user", "xiaomei@lxiaomei.com");
//		// 访问SMTP服务时需要提供的密码
//		props.put("mail.password", "19921001ZWGzwg");
//		try{
//// 构建授权信息，用于进行SMTP进行身份验证
//			Authenticator authenticator = new Authenticator() {
//				@Override
//				protected PasswordAuthentication getPasswordAuthentication() {
//					// 用户名、密码
//					String userName = props.getProperty("mail.user");
//					String password = props.getProperty("mail.password");
//					return new PasswordAuthentication(userName, password);
//				}
//			};
//			// 使用环境属性和授权信息，创建邮件会话
//			Session mailSession = Session.getInstance(props, authenticator);
//			// 创建邮件消息
//			MimeMessage message = new MimeMessage(mailSession);
//			// 设置发件人
//			InternetAddress form = new InternetAddress(
//					props.getProperty("mail.user"));
//			message.setFrom(form);
//
//			// 设置收件人
//			InternetAddress to = new InternetAddress("zhangwg@baihe.com");
//			message.setRecipient(MimeMessage.RecipientType.TO, to);
//
//			// 设置邮件标题
//			message.setSubject("猪");
////			// 设置邮件的内容体
////			message.setContent("谢谢你给我动力", "text/html;charset=UTF-8");
//			/******************************************************************************************************/
//			//邮件正文
//			MimeMultipart multipart = new MimeMultipart("mixed");
//			message.setContent(multipart);
//			 /*
//         * 创建邮件的内容
//         * 包括一个邮件正文和两个附件
//         */
//			MimeBodyPart content = new MimeBodyPart();      //邮件内容
//			//将邮件内容添加到multipart中
//			multipart.addBodyPart(content);
//        /*
//         * 设置内容（正文）---是一个复杂体
//         * 包括HTML正文和显示一张图片
//         */
//			MimeMultipart bodyMultipart = new MimeMultipart("related");
//			content.setContent(bodyMultipart);
//			//构造正文
//			MimeBodyPart htmlBody = new MimeBodyPart();
//			bodyMultipart.addBodyPart(htmlBody);
//			htmlBody.setContent(	"<div style='background-image:url(D:/8.png);height:100%;width:100%color:red;'>感谢你注册微信公众平台。 \n" +
//					"你的登录邮箱为：zhangwg@baihe.com。请点击以下链接激活帐号：</div>", "text/html;charset=UTF-8");
//
//
//			message.saveChanges();        //生成邮件
//			/******************************************************************************************************/
//			// 发送邮件
//			Transport.send(message);
//		}catch (Exception e){
//			e.printStackTrace();
//			System.out.println("resulthtml=========="+e.getMessage());
//		}
//	}

}
