package com.shiep.fxbankcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 启用Feign
@EnableFeignClients(basePackages = "com.shiep.fxbankcard.endpoint")
// 启用断路器
@EnableCircuitBreaker
public class FxBankcardApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxBankcardApplication.class, args);
    }

}
