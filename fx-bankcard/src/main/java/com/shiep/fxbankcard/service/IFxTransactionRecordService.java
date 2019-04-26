package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

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
     * @param type     交易类型
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByType(Integer type, Pageable pageable);

    /**
     * description: 通过货币码查找交易记录
     *
     * @param currencyCode 货币码
     * @param pageable     分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByCurrency(String currencyCode, Pageable pageable);

    /**
     * description: 以0，1表示time是否有值。00返回null；10查询交易时间在beginTime之前的交易记录；
     * 01查询交易时间在endTime之后的交易记录；11查询交易时间在beginTime和endTime之间的交易记录。
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param pageable  分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByTransactionTime(Timestamp beginTime, Timestamp endTime, Pageable pageable);

    /**
     * description: 银行卡号码不能为空，其他四个参数可为为空。多功能查询该银行卡的交易记录。
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param type         交易类型
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param pageable     分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> query(String bankcardID, String currencyCode, Integer type, Timestamp beginTime, Timestamp endTime, Pageable pageable);
}
