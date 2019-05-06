package com.shiep.fxauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "com.shiep.fxauth" })
@EnableFeignClients(basePackages = {"com.shiep.fxauth.endpoint"})
@EnableCircuitBreaker
@EnableZuulProxy
@EnableCaching
@EnableScheduling
public class FxAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxAuthApplication.class, args);
    }
}
