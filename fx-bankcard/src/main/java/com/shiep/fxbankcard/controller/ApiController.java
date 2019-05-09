package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.api.FxRateApi;
import com.shiep.fxbankcard.api.HeadlinesApi;
import com.shiep.fxbankcard.api.VerificationIDCardApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Object> getRmbRate() {
        logger.info("invoking CNY quotation");
        Map<String, Object> cnyRate = FxRateApi.getRmbRate();
        logger.info(cnyRate.toString());
        return cnyRate;
    }

    @GetMapping("/fxRate")
    public Map<String, Object> getFxRate() {
        logger.info("invoking FX quotation");
        Map<String, Object> fxRate = FxRateApi.getFxRate();
        logger.info(fxRate.toString());
        return fxRate;
    }

    @GetMapping("/headlines/{type}/{size}")
    public Map<String, Object> getHeadlinesPageable(@PathVariable("type") String type, @PathVariable("size") Integer size) {
        logger.info("invoking headlines api with pageable");
        Map<String, Object> headlinesPageable = HeadlinesApi.getHeadlinesPageable(type, size);
        logger.info(headlinesPageable.toString());
        return headlinesPageable;
    }

    @GetMapping("/headlines/{type}")
    public Map<String, Object> getHeadlines(@PathVariable("type") String type) {
        logger.info("invoking headlines api with type = " + type);
        Map<String, Object> headlines = HeadlinesApi.getHeadlines(type);
        logger.info(headlines.toString());
        return headlines;
    }

    @GetMapping("/headlines")
    public Map<String, Object> getHeadlines() {
        logger.info("invoking headlines api with type = international");
        Map<String, Object> headlines = HeadlinesApi.getHeadlines(HeadlinesApi.TYPE_INTERNATIONAL);
        logger.info(headlines.toString());
        return headlines;
    }

    @GetMapping("/verify/{idCardNum}")
    public Map<String, Object> verify(@PathVariable("idCardNum") String idCardNum) {
        logger.info("verification id number " + idCardNum);
        Map<String, Object> result = VerificationIDCardApi.verify(idCardNum);
        logger.info(result.toString());
        return result;
    }
}
