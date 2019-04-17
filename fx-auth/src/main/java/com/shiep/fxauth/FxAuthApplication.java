package com.shiep.fxauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = { "com.shiep.fxauth" })
@EnableFeignClients(basePackages = {"com.shiep.fxauth.endpoint"})
@EnableCircuitBreaker
@EnableZuulProxy
@EnableCaching
public class FxAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxAuthApplication.class, args);
    }
}
