package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxBankCard;
import com.shiep.fxauth.model.FxUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
     * description: 通过邮箱查找该用户
     *
     * @param email 邮箱
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @GetMapping("/user/email/{email}")
    FxUser getByEmail(@PathVariable("email") String email);

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

    /**
     * description: 通过用户ID查找该用户的银行卡
     *
     * @param userID 用户ID
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @GetMapping("/bankcard/userID/{userID}")
    FxBankCard findByUserID(@PathVariable("userID") String userID);

    /**
     * description: 通过银行卡状态查找银行卡
     *
     * @param status 银行卡状态
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/bankcard/status/{status}")
    List<FxBankCard> findByStatus(@PathVariable("status") Integer status);

    /**
     * description: 查找银行卡创建时间在time之后的银行卡
     *
     * @param time 时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/bankcard/after/{time}")
    List<FxBankCard> findByCreatedTimeAfter(@PathVariable("time") Timestamp time);

    /**
     * description: 查找银行卡创建时间在time之前的银行卡
     *
     * @param time 时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/bankcard/before/{time}")
    List<FxBankCard> findByCreatedTimeBefore(@PathVariable("time") Timestamp time);

    /**
     * description: 查找银行卡创建时间在beginTime至endTime之间的银行卡
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/bankcard/between/{beginTime}/{endTime}")
    List<FxBankCard> findByCreatedTimeBetween(@PathVariable("beginTime") Timestamp beginTime, @PathVariable("endTime") Timestamp endTime);

    /**
     * description: 通过银行卡号码查找银行卡
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @GetMapping("/bankcard/id/{id}")
    FxBankCard findByBankCardId(@PathVariable("id") String bankCardId);

    /**
     * description: 创建银行卡（状态为未激活）
     *
     * @param createdPlace 用户的IP地址
     * @param userID       用户信息id
     * @return com.shiep.fxauth.model.FxBankCard
     */
    @PostMapping("/bankcard/create/{createdPlace}/{userID}")
    FxBankCard createInitBankCard(@PathVariable("createdPlace") String createdPlace, @PathVariable("userID") String userID);

    /**
     * description: 激活银行卡
     *
     * @param bankCardId 银行卡号码
     * @return java.lang.Boolean
     */
    @PutMapping("/bankcard/active/{bankCardId}")
    Boolean activeBankCard(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 冻结银行卡
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @PutMapping("/bankcard/freeze/{bankCardId}")
    FxBankCard freezeBankCard(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 银行卡解冻
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @PutMapping("/bankcard/unfreeze/{bankCardId}")
    FxBankCard unFreezeBankCard(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 更改银行卡密码
     *
     * @param bankCardId  银行卡号码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @PutMapping("/bankcard/password")
    FxBankCard updatePassword(@RequestParam("bankCardId") String bankCardId, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword);


    /**
     * description: 重置初始密码
     *
     * @param bankCardId 银行卡号码
     * @return java.lang.String
     */
    @PutMapping("/bankcard/reset/{bankCardId}")
    String resetInitPassword(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 注销银行卡(逻辑删除，设置银行卡状态为销户)
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @DeleteMapping("/bankcard/{bankCardId}")
    FxBankCard deleteBankCard(@PathVariable("bankCardId") String bankCardId);
}
