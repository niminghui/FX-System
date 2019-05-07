package com.shiep.fxauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 倪明辉
 * @date: 2019/5/6 13:40
 * @description:
 */

@Controller
@RequestMapping("/stomp")
public class StompController {
    int count = 0;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/page")
    public String toPage() {
        return "stompPage";
    }

    /**
     * description: 每隔分钟发送一次消息
     *
     * @param
     * @return void
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void sendMsg() {
        System.out.println("/stomp/sub/chat:" + count++);
        simpMessagingTemplate.convertAndSend("/stomp/sub/chat", "hi");
    }
}
