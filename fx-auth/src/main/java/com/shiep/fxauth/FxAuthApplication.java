package com.shiep.fxauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = { "com.shiep.fxauth" })
@EnableFeignClients(basePackages = {"com.shiep.fxauth.endpoint"})
@EnableCircuitBreaker
@EnableZuulProxy
public class FxAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxAuthApplication.class, args);
    }

}
