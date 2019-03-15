package com.shiep.fxaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 启用Feign
@EnableFeignClients(basePackages = "com.shiep.fxaccount.endpoint")
// 启用断路器
@EnableCircuitBreaker
public class FxAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxAccountApplication.class, args);
    }

}
