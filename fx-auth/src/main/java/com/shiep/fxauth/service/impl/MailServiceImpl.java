package com.shiep.fxauth.service.impl;

import com.shiep.fxauth.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/4/11 10:33
 * @description: 邮件服务接口的实现类==》异步方式发送邮件
 */
@Service
public class MailServiceImpl implements IMailService {
    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendTextMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            logger.info("Async send mail by thread " + "[" + Thread.currentThread().getName() + "]");
            mailSender.send(message);
        } catch (MailException e) {
            logger.error("纯文本邮件发送失败->message:{}", e.getMessage());
            throw new RuntimeException("邮件发送失败");
        }
    }

    @Async
    @Override
    public void sendThymeleafMail(String to, String subject, String thymeleafName, Map<String, Object> thymeleafValue) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            Context context = new Context();
            // 定义模板数据
            context.setVariables(thymeleafValue);
            // 获取thymeleaf的html模板
            String emailContent = templateEngine.process(thymeleafName, context);
            messageHelper.setText(emailContent, true);
            //发送邮件
            mailSender.send(mimeMessage);
            logger.info("Async send mail by thread " + "[" + Thread.currentThread().getName() + "]");
        } catch (MessagingException e) {
            logger.error("模板邮件发送失败->message:{}", e.getMessage());
            throw new RuntimeException("邮件发送失败");
        }
    }
}
