package com.zh.dubbo.manage.aliyun.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.zh.dubbo.dao.CommonDao;
import com.zh.dubbo.entity.Config;
import com.zh.dubbo.enttity.EmailTemplate;
import com.zh.dubbo.manage.aliyun.EmailAliyunService;
import com.zh.dubbo.untils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by 70214 on 2017/6/3.
 */
@Service
public class EmailAliyunServiceImpl implements EmailAliyunService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.profiles.active}")
    private String env;
    @Autowired
    private CommonDao commonDao;
    @Override
    public Map<String, Object> emailSingleSend(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("template_nid") == null || "".equals(params.get("template_nid").toString())){
            throw new Exception("邮件模版nid不能为空！");
        }
        if(params.get("to_email") == null || "".equals(params.get("to_email").toString())){
            throw new Exception("收件人不能为空！");
        }
        if(env == null){
            throw new Exception("环境配置不能为空");
        }
        //收件人地址
        String to_email = params.get("to_email").toString();
        List<Config> emailConfList = commonDao.getConfigList("aliyun_");
        //阿里云邮件是否打开
        String status = StringUtils.getConfFromList(emailConfList,"aliyun_status");
        if(!"1".equals(status)){
            throw new Exception("阿里云邮件发送系统暂未开启，请开启后发送！");
        }
        String template_nid = params.get("template_nid").toString();
        EmailTemplate emailTemplate = commonDao.getSmsTemplateByNid(template_nid);
        if(emailTemplate == null){
            throw new Exception("获取邮件模版失败！");
        }
        int tmeplate_status = emailTemplate.getTempleteStatus();
        if(tmeplate_status != 1){
            throw new Exception(template_nid+"模版尚未开启，请开启后再发送！");
        }
        //邮件主题
        String subject = emailTemplate.getTempleteName();
        //邮件正文
        String email_content = emailTemplate.getTempleteValue();
        if(params.get("template_param") != null && !"".equals(params.get("template_param").toString())){
            Map<String,Object> templateParam = (Map)params.get("template_params");
            String template_content = emailTemplate.getTempleteValue();
            email_content = StringUtils.replaceMap(template_content,templateParam);
        }
        if("".equals(email_content)){
            throw new Exception("邮件内容不能为空！");
        }
        //如果是测试环境则只保留发送日志信息
        if("dev".equals(env)){

        }
        //邮件
        //smtp方式发送端口号
        String port = StringUtils.getConfFromList(emailConfList,"aliyun_smtp_port2");
        //smtp方式发送host
        String host = StringUtils.getConfFromList(emailConfList,"aliyun_smtp_host");
        //smtp方式发送发件人帐号
        String user = StringUtils.getConfFromList(emailConfList,"aliyun_user");
        //smtp方式发送密码
        String password = StringUtils.getConfFromList(emailConfList,"aliyun_smtp_pwd");
        //smtp方式发送背景图
        String background_file = StringUtils.getConfFromList(emailConfList,"aliyun_background");
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
//		props.put("mail.smtp.port", ALIDM_SMTP_PORT);
        // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.port", port);


        // 发件人的账号
        props.put("mail.user", user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", password);
        try{
        // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人
            InternetAddress to = new InternetAddress(to_email);
            message.setRecipient(MimeMessage.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(subject);
//			// 设置邮件的内容体
            //邮件正文
            MimeMultipart multipart = new MimeMultipart("mixed");
            message.setContent(multipart);
			 /*
         * 创建邮件的内容
         * 包括一个邮件正文和两个附件
         */
            MimeBodyPart content = new MimeBodyPart();      //邮件内容
            //将邮件内容添加到multipart中
            multipart.addBodyPart(content);
        /*
         * 设置内容（正文）---是一个复杂体
         * 包括HTML正文和显示一张图片
         */
            MimeMultipart bodyMultipart = new MimeMultipart("related");
            content.setContent(bodyMultipart);
            //构造正文
            MimeBodyPart htmlBody = new MimeBodyPart();
            bodyMultipart.addBodyPart(htmlBody);
            htmlBody.setContent("<div style='background-image:url(D:/8.png);height:100%;width:100%;color:green; background-repeat:repeat; background-position:100% 100%; background-attachment:fixed; text-indent:em; '>"+ email_content.trim()+"</div><br>", "text/html;charset=UTF-8");
//            System.out.println("email_content============="+email_content);
//            htmlBody.setContent("<div style=\'background-image:url(cid:8.png);height:100%;width:100%;color:green; background-repeat:repeat; background-position:100% 100%; background-attachment:fixed; text-indent:em; \'>"+email_content+"</div><br>" +
//                    "", "text/html;charset=UTF-8");

            message.saveChanges();        //生成邮件
            /******************************************************************************************************/
            // 发送邮件
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Objects> emailBatchSend(Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> emailSendAttachment(Map<String, Object> params) throws Exception {
        return null;
    }
}
