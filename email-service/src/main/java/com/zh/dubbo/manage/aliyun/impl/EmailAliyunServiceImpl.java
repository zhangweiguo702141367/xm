package com.zh.dubbo.manage.aliyun.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONObject;
import com.zh.dubbo.dao.CommonDao;
import com.zh.dubbo.entity.Config;
import com.zh.dubbo.enttity.EmailTemplate;
import com.zh.dubbo.manage.aliyun.EmailAliyunService;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;

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
        Map<String,Object> logMap = new HashMap<>();
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
            Map<String,Object> templateParam = (Map)params.get("template_param");
            params.put("template_param_log", JSON.json(templateParam));
            String template_content = emailTemplate.getTempleteValue();
            email_content = StringUtils.replaceMap(template_content,templateParam);
        }else{
            params.put("template_param_log","通知型邮件");
        }
        System.out.println("email_content=========="+email_content);
        if("".equals(email_content)){
            throw new Exception("邮件内容不能为空！");
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
        //如果是测试环境则只保留发送日志信息

        logMap.put("to_email",to_email);
        logMap.put("from_email",user);
        logMap.put("subject",subject);
        logMap.put("html_body",email_content);
        logMap.put("request_data",params.get("template_param_log"));
        logMap.put("is_batch",2);
        logMap.put("is_attachment",2);
        logMap.put("create_time", DateUtil.getCurrentTime());
        logMap.put("env",env);
        if("dev".equals(env)){
            logMap.put("send_status",1);
            commonDao.insetEmailLog(logMap);
            return null;
        }

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
            //设置邮件发送时间
            message.setSentDate(new Date());
//			// 设置邮件的内容体
            //邮件正文
            MimeMultipart multipart = new MimeMultipart("mixed");
//            message.setContent(multipart);
            message.setContent(email_content,"text/html;charset=utf-8");
            message.saveChanges();        //生成邮件
            /******************************************************************************************************/
            // 发送邮件
            Transport.send(message);
            //发送成功
            logMap.put("send_status",1);
            commonDao.insetEmailLog(logMap);
        }catch (Exception e){
            //发送失败
            logMap.put("send_status",2);
            try{
                commonDao.insetEmailLog(logMap);
            }catch (Exception e1){
                logger.error(e1.getMessage());
            }
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Objects> emailBatchSend(Map<String, Object> params) throws Exception {
        Map<String,Object> logMap = new HashMap<>();
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
        params.put("template_param_log","通知型邮件");
        System.out.println("email_content=========="+email_content);
        //邮件
        //smtp方式发送端口号
        String port = StringUtils.getConfFromList(emailConfList,"aliyun_smtp_port2");
        //smtp方式发送host
        String host = StringUtils.getConfFromList(emailConfList,"aliyun_smtp_host");
        //smtp方式发送发件人帐号
        String user = StringUtils.getConfFromList(emailConfList,"aliyun_user");
        //smtp方式发送密码
        String password = StringUtils.getConfFromList(emailConfList,"aliyun_smtp_pwd");
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
        //如果是测试环境则只保留发送日志信息

        logMap.put("to_email","batchemail");
        logMap.put("from_email",user);
        logMap.put("subject",subject);
        logMap.put("html_body",email_content);
        logMap.put("request_data",params.get("template_param_log"));
        logMap.put("is_batch",1);
        logMap.put("is_attachment",2);
        logMap.put("batch_emails",to_email);
        logMap.put("create_time", DateUtil.getCurrentTime());
        logMap.put("env",env);
        if("dev".equals(env)){
            logMap.put("send_status",1);
            commonDao.insetEmailLog(logMap);
            return null;
        }

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
            String[] to_emails = to_email.split(";");
            int i = 0;
            InternetAddress[] addresses = new InternetAddress[to_emails.length];
            for (i=0;i<to_emails.length;i++) {
                addresses[i] = new InternetAddress(to_emails[i]);
            }
            // 设置收件人
//            InternetAddress to = new InternetAddress(to_email);
            message.setRecipients(MimeMessage.RecipientType.TO, addresses);
            // 设置邮件标题
            message.setSubject(subject);
            //设置邮件发送时间
            message.setSentDate(new Date());
//			// 设置邮件的内容体
            //邮件正文
            MimeMultipart multipart = new MimeMultipart("mixed");
//            message.setContent(multipart);
            message.setContent(email_content,"text/html;charset=utf-8");
            message.saveChanges();        //生成邮件
            /******************************************************************************************************/
            // 发送邮件
            Transport.send(message);
            //发送成功
            logMap.put("send_status",1);
            commonDao.insetEmailLog(logMap);
        }catch (Exception e){
            //发送失败
            logMap.put("send_status",2);
            try{
                commonDao.insetEmailLog(logMap);
            }catch (Exception e1){
                logger.error(e1.getMessage());
            }
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Object> emailSendAttachment(Map<String, Object> params) throws Exception {
        Map<String,Object> logMap = new HashMap<>();
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("template_nid") == null || "".equals(params.get("template_nid").toString())){
            throw new Exception("邮件模版nid不能为空！");
        }
        if(params.get("to_email") == null || "".equals(params.get("to_email").toString())){
            throw new Exception("收件人不能为空！");
        }
        if(params.get("attachment") == null || "".equals(params.get("attachment").toString())){
            throw new Exception("附件内容不能为空！");
        }
        String attachment = params.get("attachment").toString();
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
        //附件内容
        Map<String,Object> attachments = (Map)params.get("attachment");
        //邮件主题
        String subject = emailTemplate.getTempleteName();
        //邮件正文
        String email_content = emailTemplate.getTempleteValue();
        if(params.get("template_param") != null && !"".equals(params.get("template_param").toString())){
            Map<String,Object> templateParam = (Map)params.get("template_param");
            params.put("template_param_log", JSON.json(templateParam));
            String template_content = emailTemplate.getTempleteValue();
            email_content = StringUtils.replaceMap(template_content,templateParam);
        }else{
            params.put("template_param_log","通知型邮件");
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
        //如果是测试环境则只保留发送日志信息

        logMap.put("to_email",to_email);
        logMap.put("from_email",user);
        logMap.put("subject",subject);
        logMap.put("html_body",email_content);
        logMap.put("request_data",params.get("template_param_log"));
        logMap.put("is_batch",2);
        logMap.put("is_attachment",1);
        logMap.put("attachment",JSON.json(attachments));
        logMap.put("batch_emails",to_email);
        logMap.put("create_time", DateUtil.getCurrentTime());
        logMap.put("env",env);
        if("dev".equals(env)){
            logMap.put("send_status",1);
            commonDao.insetEmailLog(logMap);
            return null;
        }

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
            message.setRecipients(MimeMessage.RecipientType.TO, to_email);
            // 设置邮件标题
            message.setSubject(subject);
            //设置邮件发送时间
            message.setSentDate(new Date());
            //邮件正文
            MimeMultipart multipart = new MimeMultipart("mixed");
            message.setContent(multipart);

            MimeBodyPart content = new MimeBodyPart();      //邮件内容
            multipart.addBodyPart(content);
            //添加附件

            for(String attc :attachments.keySet()){
                MimeBodyPart attch1 = new MimeBodyPart();
                multipart.addBodyPart(attch1);
                DataSource ds1 = new FileDataSource(attachments.get(attc).toString());
                DataHandler dh1 = new DataHandler(ds1);
                attch1.setDataHandler(dh1);
                attch1.setFileName(MimeUtility.encodeText(ds1.getName()));
            }
            /*
         * 设置内容（正文）---是一个复杂体
         * 包括HTML正文和显示一张图片
         */
            MimeMultipart bodyMultipart = new MimeMultipart("related");
            content.setContent(bodyMultipart);
            //构造正文
            MimeBodyPart htmlBody = new MimeBodyPart();
            bodyMultipart.addBodyPart(htmlBody);
            //设置HTML正文

            htmlBody.setContent(email_content, "text/html;charset=UTF-8");
            message.saveChanges();        //生成邮件
            /******************************************************************************************************/
            // 发送邮件
            Transport.send(message);
            //发送成功
            logMap.put("send_status",1);
            commonDao.insetEmailLog(logMap);
        }catch (Exception e){
            //发送失败
            logMap.put("send_status",2);
            try{
                commonDao.insetEmailLog(logMap);
            }catch (Exception e1){
                logger.error(e1.getMessage());
            }
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return null;
    }
}
