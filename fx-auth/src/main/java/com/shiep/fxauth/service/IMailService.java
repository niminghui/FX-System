package com.shiep.fxauth.service;

import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/4/11 10:18
 * @description: 邮件服务接口
 */
public interface IMailService {
    /**
     * description: 发送文本邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return void
     */
    void sendTextMail(String to, String subject, String content);

    /**
     * description: 发送Thymeleaf模板邮件
     *
     * @param to             收件人
     * @param subject        邮件主题
     * @param thymeleafName  Thymeleaf模板名称
     * @param thymeleafValue Thymeleaf模板的动态值
     * @return void
     */
    void sendThymeleafMail(String to, String subject, String thymeleafName, Map<String, Object> thymeleafValue);
}
