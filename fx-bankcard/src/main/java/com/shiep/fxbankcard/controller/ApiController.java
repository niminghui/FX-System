package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.api.FxRateApi;
import com.shiep.fxbankcard.api.HeadlinesApi;
import com.shiep.fxbankcard.api.VerificationIDCardApi;
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
    @GetMapping("/rmbRate")
    public Map<String, Object> getRmbRate() {
        return FxRateApi.getRmbRate();
    }

    @GetMapping("/fxRate")
    public Map<String, Object> getFxRate() {
        return FxRateApi.getFxRate();
    }

    @GetMapping("/headlines/{type}/{size}")
    public Map<String, Object> getHeadlinesPageable(@PathVariable("type") String type, @PathVariable("size") Integer size) {
        return HeadlinesApi.getHeadlinesPageable(type, size);
    }

    @GetMapping("/headlines/{type}")
    public Map<String, Object> getHeadlines(@PathVariable("type") String type) {
        return HeadlinesApi.getHeadlines(type);
    }

    @GetMapping("/headlines")
    public Map<String, Object> getHeadlines() {
        return HeadlinesApi.getHeadlines(HeadlinesApi.TYPE_INTERNATIONAL);
    }

    @GetMapping("/verify/{idCardNum}")
    public Map<String, Object> verify(@PathVariable("idCardNum") String idCardNum) {
        return VerificationIDCardApi.verify(idCardNum);
    }
}
