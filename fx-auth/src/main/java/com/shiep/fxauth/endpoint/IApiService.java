package com.shiep.fxauth.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/4/22 13:30
 * @description: Api服务端点
 */
public interface IApiService {

    /**
     * description: 返回人民币牌价
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    @GetMapping("/api/rmbRate")
    Map<String, Object> getRmbRate();

    /**
     * description: 返回外汇牌价
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    @GetMapping("/api/fxRate")
    Map<String, Object> getFxRate();

    /**
     * description: 返回带分页的新闻头条
     *
     * @param type 新闻类型
     * @param size 分页大小
     * @return java.util.Map<java.lang.String   ,   java.lang.Object>
     */
    @GetMapping("/api/headlines/{type}/{size}")
    public Map<String, Object> getHeadlinesPageable(@PathVariable("type") String type, @PathVariable("size") Integer size);

    /**
     * description: 返回新闻头条
     *
     * @param type 新闻类型
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    @GetMapping("/api/headlines/{type}")
    Map<String, Object> getHeadlines(@PathVariable("type") String type);

    /**
     * description: 返回国际新闻头条
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    @GetMapping("/api/headlines")
    Map<String, Object> getHeadlines();

    /**
     * description: 返回校验身份证结果
     *
     * @param idCardNum 身份证号码
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    @GetMapping("/api/verify/{idCardNum}")
    Map<String, Object> verify(@PathVariable("idCardNum") String idCardNum);
}
