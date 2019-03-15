package com.shiep.fxserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
// 启用Eureka
@EnableEurekaServer
// 启用Hystrix仪表盘
@EnableHystrixDashboard
public class FxServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxServerApplication.class, args);
    }

}
