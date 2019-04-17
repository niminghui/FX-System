package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxBankCard;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/15 10:33
 * @description: 银行卡服务接口
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxBankCardService {
    /**
     * description: 通过用户ID查找该用户的银行卡
     *
     * @param userID 用户ID
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    FxBankCard findByUserID(String userID);

    /**
     * description: 通过银行卡状态查找银行卡
     *
     * @param status 银行卡状态
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByStatus(Integer status);

    /**
     * description: 查找银行卡创建时间在time之后的银行卡
     *
     * @param time 时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByCreatedTimeAfter(Timestamp time);

    /**
     * description: 查找银行卡创建时间在time之前的银行卡
     *
     * @param time 时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByCreatedTimeBefore(Timestamp time);

    /**
     * description: 查找银行卡创建时间在beginTime至endTime之间的银行卡
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByCreatedTimeBetween(Timestamp beginTime, Timestamp endTime);

    /**
     * description: 通过银行卡号码查找银行卡
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    FxBankCard findByBankCardId(String bankCardId);

    /**
     * description: 创建银行卡（状态为未激活）
     *
     * @param createdPlace 用户的IP地址
     * @param userID 用户信息id
     * @return com.shiep.fxauth.model.FxBankCard
     */
    FxBankCard createInitBankCard(String createdPlace, String userID);

    /**
     * description: 激活银行卡
     *
     * @param bankCardId 银行卡号码
     * @return java.lang.Boolean
     */
    Boolean activeBankCard(String bankCardId);

    /**
     * description: 冻结银行卡
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    FxBankCard freezeBankCard(String bankCardId);

    /**
     * description: 银行卡解冻
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    FxBankCard unFreezeBankCard(String bankCardId);

    /**
     * description: 更改银行卡密码
     *
     * @param bankCardId  银行卡号码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    FxBankCard updatePassword(String bankCardId, String oldPassword, String newPassword);

    /**
     * description: 重置初始密码
     *
     * @param bankCardId 银行卡号码
     * @return java.lang.String
     */
    String resetInitPassword(String bankCardId);
    /**
     * description: 注销银行卡(逻辑删除，设置银行卡状态为销户)
     *
     * @param bankCardId 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    FxBankCard deleteBankCard(String bankCardId);
}
