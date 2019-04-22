package com.shiep.fxauth.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.shiep.fxauth.endpoint.IApiService;
import com.shiep.fxauth.endpoint.ICurrencyPairsService;
import com.shiep.fxauth.endpoint.ICurrencyService;
import com.shiep.fxauth.endpoint.IUserInfoService;
import feign.Contract;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 16:39
 * @description: 自定义Feign实例配置(实现同一个服务ID ， 多个FeignClient问题)
 */
@Configuration
public class FeignClientConfig {
    /**
     * description: 通过该对象获得构造Feign时需要的encoder, decoder, client
     */
    @Autowired
    private FeignContext feignContext;

    /**
     * description: 通过注入Eureka实例对象，就不用手动指定url，只需要指定服务名即可
     */
    @SuppressWarnings("all")
    @Autowired
    private EurekaClient eurekaClient;

    /**
     * description: 构建Feign Client
     *
     * @param clazz    feign client 类
     * @param serverId 服务ID
     * @return T
     */
    private <T> T create(Class<T> clazz, String serverId) {
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka(serverId, false);
        return Feign.builder()
                .encoder(feignContext.getInstance(serverId, feign.codec.Encoder.class))
                .decoder(feignContext.getInstance(serverId, feign.codec.Decoder.class))
                .contract(feignContext.getInstance(serverId, Contract.class))
                .target(clazz, nextServerFromEureka.getHomePageUrl());
    }

    /**
     * description: 配置api服务端点bean
     *
     * @param
     * @return com.shiep.fxauth.endpoint.IApiService
     */
    @Bean
    public IApiService apiService() {
        return create(IApiService.class, "fx-bankcard");
    }

    /**
     * description: 配置用户信息服务端点bean
     *
     * @param
     * @return com.shiep.fxauth.endpoint.IUserInfoService
     */
    @Bean
    public IUserInfoService userInfoService() {
        return create(IUserInfoService.class, "fx-bankcard");
    }

    /**
     * description: 配置货币服务端点bean
     *
     * @param
     * @return com.shiep.fxauth.endpoint.ICurrencyService
     */
    @Bean
    public ICurrencyService fxCurrencyService() {
        return create(ICurrencyService.class, "fx-bankcard");
    }

    /**
     * description: 配置货币对服务端点bean
     *
     * @param
     * @return com.shiep.fxauth.endpoint.ICurrencyPairsService
     */
    @Bean
    public ICurrencyPairsService currencyPairsService() {
        return create(ICurrencyPairsService.class, "fx-bankcard");
    }
}
