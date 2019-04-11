package com.shiep.fxauth.service.impl;

import com.shiep.fxauth.service.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/4/11 11:25
 * @description: 测试发送邮件
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {
    @Autowired
    private IMailService mailService;

    @Test
    public void sendTextMail() {
        mailService.sendTextMail("635681077@qq.com", "FX-System：测试发送邮件", "Hello world. Nice to see you.");
    }

    @Test
    public void sendThymeleafMail() {
        Map<String, Object> value = new HashMap<>();
        value.put("message", "thymeleaf");
        mailService.sendThymeleafMail("635681077@qq.com", "FX-System：测试发送Thymeleaf邮件", "mailActive", value);
    }
}