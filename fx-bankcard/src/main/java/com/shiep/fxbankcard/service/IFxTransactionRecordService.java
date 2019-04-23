package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 10:28
 * @description: 交易记录服务接口（增、查操作）
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxTransactionRecordService {
    /**
     * description: 增加一条交易记录
     *
     * @param transactionRecord 交易记录
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    FxTransactionRecord create(FxTransactionRecord transactionRecord);

    /**
     * description: 通过交易类型查找所有的交易记录
     *
     * @param type 交易类型
     * @return java.util.List<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    List<FxTransactionRecord> findByType(Integer type);

    /**
     * description: 通过货币码查找交易记录
     *
     * @param currencyCode 货币码
     * @return java.util.List<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    List<FxTransactionRecord> findByCurrency(String currencyCode);

    /**
     * description: 以0，1表示time是否有值。00返回null；10查询交易时间在beginTime之前的交易记录；
     * 01查询交易时间在endTime之后的交易记录；11查询交易时间在beginTime和endTime之间的交易记录。
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    List<FxTransactionRecord> findByTransactionTime(Timestamp beginTime, Timestamp endTime);

    /**
     * description: 银行卡号码不能为空，其他四个参数可为为空。多功能查询该银行卡的交易记录。
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param type         交易类型
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    List<FxTransactionRecord> query(String bankcardID, String currencyCode, Integer type, Timestamp beginTime, Timestamp endTime);
}
