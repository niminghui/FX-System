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
@FeignClient(name = "fx-bankcard", path = "/bankcard")
public interface IBankCardService {

    /**
     * description: 通过用户ID查找该用户的银行卡
     *
     * @param userID 用户ID
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @GetMapping("/userID/{userID}")
    FxBankCard findByUserID(@PathVariable("userID") String userID);

    /**
     * description: 通过银行卡状态查找银行卡
     *
     * @param status 银行卡状态
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/status/{status}")
    List<FxBankCard> findByStatus(@PathVariable("status") Integer status);

    /**
     * description: 查找银行卡创建时间在time之后的银行卡
     *
     * @param time 时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/after/{time}")
    List<FxBankCard> findByCreatedTimeAfter(@PathVariable("time") Timestamp time);

    /**
     * description: 查找银行卡创建时间在time之前的银行卡
     *
     * @param time 时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/before/{time}")
    List<FxBankCard> findByCreatedTimeBefore(@PathVariable("time") Timestamp time);

    /**
     * description: 查找银行卡创建时间在beginTime至endTime之间的银行卡
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    @GetMapping("/between/{beginTime}/{endTime}")
    List<FxBankCard> findByCreatedTimeBetween(@PathVariable("beginTime") Timestamp beginTime, @PathVariable("endTime") Timestamp endTime);

    /**
     * description: 通过银行卡号码查找银行卡
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @GetMapping("/id/{id}")
    FxBankCard findByBankCardId(@PathVariable("id") String bankCardId);

    /**
     * description: 创建银行卡（状态为未激活）
     *
     * @param createdPlace 用户的IP地址
     * @param userID       用户信息id
     * @return com.shiep.fxauth.model.FxBankCard
     */
    @PostMapping("/create/{createdPlace}/{userID}")
    FxBankCard createInitBankCard(@PathVariable("createdPlace") String createdPlace, @PathVariable("userID") String userID);

    /**
     * description: 激活银行卡
     *
     * @param bankCardId 银行卡号码
     * @return java.lang.Boolean
     */
    @PutMapping("/active/{bankCardId}")
    Boolean activeBankCard(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 冻结银行卡
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @PutMapping("/freeze/{bankCardId}")
    FxBankCard freezeBankCard(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 银行卡解冻
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @PutMapping("/unfreeze/{bankCardId}")
    FxBankCard unFreezeBankCard(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 更改银行卡密码
     *
     * @param bankCardId  银行卡号码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @PutMapping("/password")
    FxBankCard updatePassword(@RequestParam("bankCardId") String bankCardId, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword);


    /**
     * description: 重置初始密码
     *
     * @param bankCardId 银行卡号码
     * @return java.lang.String
     */
    @PutMapping("/reset/{bankCardId}")
    String resetInitPassword(@PathVariable("bankCardId") String bankCardId);

    /**
     * description: 注销银行卡(逻辑删除，设置银行卡状态为销户)
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    @DeleteMapping("/{bankCardId}")
    FxBankCard deleteBankCard(@PathVariable("bankCardId") String bankCardId);
}
