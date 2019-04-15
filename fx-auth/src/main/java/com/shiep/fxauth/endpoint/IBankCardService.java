package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/4/3 16:52
 * @description: fx-bankcard服务端点
 */
@FeignClient("fx-bankcard")
public interface IBankCardService {
    /**
     * description: 返回人民币牌价
     *
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/api/rmbRate")
    Map<String, Object> getRmbRate();

    /**
     * description: 返回外汇牌价
     *
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/api/fxRate")
    Map<String, Object> getFxRate();

    /**
     * description: 返回带分页的新闻头条
     *
     * @param type 新闻类型
     * @param size 分页大小
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    @GetMapping("/api/headlines/{type}/{size}")
    public Map<String, Object> getHeadlinesPageable(@PathVariable("type") String type, @PathVariable("size") Integer size);

    /**
     * description: 返回新闻头条
     *
     * @param type 新闻类型
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/api/headlines/{type}")
    Map<String, Object> getHeadlines(@PathVariable("type") String type);

    /**
     * description: 返回国际新闻头条
     *
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/api/headlines")
    Map<String, Object> getHeadlines();

    /**
     * description: 返回校验身份证结果
     *
     * @param idCardNum 身份证号码
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/api/verify/{idCardNum}")
    Map<String, Object> verify(@PathVariable("idCardNum") String idCardNum);

    /**
     * description: 通过中文名查找用户
     *
     * @param chineseName 中文名
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @GetMapping("/user/chineseName/{chineseName}")
    List<FxUser> getByChineseName(@PathVariable("chineseName") String chineseName);

    /**
     * description: 通过身份证号码查找该用户
     *
     * @param idNumber 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @GetMapping("/user/idNumber/{idNumber}")
    FxUser getByIdCardNum(@PathVariable("idNumber") String idNumber);

    /**
     * description: 返回所有用户信息
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxUser>
     */
    @GetMapping("/user")
    List<FxUser> getAll();

    /**
     * description: 新建用户信息
     *
     * @param user FxUser
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @PostMapping("/user")
    FxUser createFxUser(FxUser user);
}
