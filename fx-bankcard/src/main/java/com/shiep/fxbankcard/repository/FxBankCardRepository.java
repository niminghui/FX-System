package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxBankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 10:35
 * @description: FxBankCard的数据访问层
 */
@Repository
public interface FxBankCardRepository extends JpaRepository<FxBankCard,String> {

    /**
     * description: 通过用户ID查找该用户的银行卡
     *
     * @param userID
     * @return com.shiep.fxbankcard.entity.FxBankCard
     */
    FxBankCard findByUserID(String userID);

    /**
     * description: 通过银行卡状态查找银行卡
     *
     * @param Status
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByStatus(Integer Status);

    /**
     * description: 查找银行卡创建时间在time之后的银行卡
     *
     * @param time
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByCreatedTimeAfter(Timestamp time);

    /**
     * description: 查找银行卡创建时间在time之前的银行卡
     *
     * @param time
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByCreatedTimeBefore(Timestamp time);

    /**
     * description: 查找银行卡创建时间在beginTime至endTime之间的银行卡
     *
     * @param beginTime
     * @param endTime
     * @return java.util.List<com.shiep.fxbankcard.entity.FxBankCard>
     */
    List<FxBankCard> findByCreatedTimeBetween(Timestamp beginTime,Timestamp endTime);
}
