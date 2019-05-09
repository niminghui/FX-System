package com.shiep.fxbankcard.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.shiep.fxbankcard.api.FxRateApi;
import com.shiep.fxbankcard.api.HeadlinesApi;
import com.shiep.fxbankcard.api.VerificationIDCardApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/4/3 15:13
 * @description: api控制层
 */
@RestController
@RequestMapping(path = "/api", produces = "application/json;charset=utf-8")
public class ApiController {
    private Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/rmbRate")
    @HystrixCommand(fallbackMethod = "getRmbRateError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Map<String, Object> getRmbRate() {
        logger.info("invoking CNY quotation");
        Map<String, Object> cnyRate = FxRateApi.getRmbRate();
        logger.info(cnyRate.toString());
        return cnyRate;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public Map<String, Object> getRmbRateError() {
        logger.warn("invoke CNY quotation timeout");
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", "408");
        result.put("message", "Request Timeout. 请求超时。");
        return result;
    }

    @GetMapping("/fxRate")
    @HystrixCommand(fallbackMethod = "getFxRateError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Map<String, Object> getFxRate() {
        logger.info("invoking FX quotation");
        Map<String, Object> fxRate = FxRateApi.getFxRate();
        logger.info(fxRate.toString());
        return fxRate;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public Map<String, Object> getFxRateError() {
        logger.warn("invoke FX quotation timeout");
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", "408");
        result.put("message", "Request Timeout. 请求超时。");
        return result;
    }

    @GetMapping("/headlines/{type}/{size}")
    @HystrixCommand(fallbackMethod = "getHeadlinesPageableError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Map<String, Object> getHeadlinesPageable(@PathVariable("type") String type, @PathVariable("size") Integer size) {
        logger.info("invoking headlines api with pageable");
        Map<String, Object> headlinesPageable = HeadlinesApi.getHeadlinesPageable(type, size);
        logger.info(headlinesPageable.toString());
        return headlinesPageable;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param type 新闻类型
     * @param size 页面大小
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public Map<String, Object> getHeadlinesPageableError(String type, Integer size) {
        logger.warn("invoking headlines api with pageable timeout");
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", "408");
        result.put("message", "Request Timeout. 请求超时。");
        return result;
    }

    @GetMapping("/headlines/{type}")
    @HystrixCommand(fallbackMethod = "getHeadlinesError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Map<String, Object> getHeadlines(@PathVariable("type") String type) {
        logger.info("invoking headlines api with type = " + type);
        Map<String, Object> headlines = HeadlinesApi.getHeadlines(type);
        logger.info(headlines.toString());
        return headlines;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param type 新闻类型
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public Map<String, Object> getHeadlinesError(String type) {
        logger.warn("invoking headlines api timeout");
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", "408");
        result.put("message", "Request Timeout. 请求超时。");
        return result;
    }

    @GetMapping("/headlines")
    @HystrixCommand(fallbackMethod = "getHeadlinesError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Map<String, Object> getHeadlines() {
        logger.info("invoking headlines api with type = international");
        Map<String, Object> headlines = HeadlinesApi.getHeadlines(HeadlinesApi.TYPE_INTERNATIONAL);
        logger.info(headlines.toString());
        return headlines;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public Map<String, Object> getHeadlinesError() {
        logger.warn("invoking headlines timeout");
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", "408");
        result.put("message", "Request Timeout. 请求超时。");
        return result;
    }

    @GetMapping("/verify/{idCardNum}")
    @HystrixCommand(fallbackMethod = "verifyError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Map<String, Object> verify(@PathVariable("idCardNum") String idCardNum) {
        logger.info("verification id number " + idCardNum);
        Map<String, Object> result = VerificationIDCardApi.verify(idCardNum);
        logger.info(result.toString());
        return result;
    }

    /**
     * description: 熔断器，降级服务
     *
     * @param idCardNum 身份证号码
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public Map<String, Object> verifyError(String idCardNum) {
        logger.warn("verify id number " + idCardNum + " timeout");
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", "408");
        result.put("message", "Request Timeout. 请求超时。");
        return result;
    }
}
