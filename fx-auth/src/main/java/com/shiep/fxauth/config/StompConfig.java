package com.shiep.fxauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author: 倪明辉
 * @date: 2019/5/6 13:34
 * @description: STOMP配置类
 */
@EnableWebSocketMessageBroker
@Configuration
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * description: 注册服务器端点
     *
     * @param registry stomp端点注册器
     * @return void
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").withSockJS();
    }

    /**
     * description: 定义服务器端点请求和订阅前缀
     *
     * @param registry stomp端点注册器
     * @return void
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端订阅路径前缀
        registry.enableSimpleBroker("/stomp/sub");
        // 服务端点请求前缀
        registry.setApplicationDestinationPrefixes("/stomp/server");
    }
}