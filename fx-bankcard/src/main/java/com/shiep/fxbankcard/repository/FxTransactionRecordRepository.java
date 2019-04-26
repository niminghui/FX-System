package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 10:51
 * @description: FxTransactionRecord的数据访问层
 */
@Repository
public interface FxTransactionRecordRepository extends JpaRepository<FxTransactionRecord, String> {

    /**
     * description: 通过银行卡号查询该银行卡所有的交易记录(带分页)
     *
     * @param bankcardID 银行卡号
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardId(String bankcardID, Pageable pageable);

    /**
     * description: 通过银行卡号和货币码查找该银行卡有关该货币的所有交易记录
     *
     * @param bankcardID 银行卡号
     * @param currencyCode 货币码
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndCurrencyCode(String bankcardID, String currencyCode, Pageable pageable);

    /**
     * description: 通过银行卡号和交易类型查找该银行卡有关该交易类型的所有交易记录
     *
     * @param bankcardID 银行卡号
     * @param type 交易类型
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndType(String bankcardID, Integer type, Pageable pageable);

    /**
     * description: 通过银行卡号码、货币码、交易类型查找交易记录
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param type         交易类型
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndCurrencyCodeAndType(String bankcardID, String currencyCode, Integer type, Pageable pageable);

    /**
     * description: 通过交易类型查找所有的交易记录
     *
     * @param type 交易类型
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByType(Integer type, Pageable pageable);

    /**
     * description: 通过货币码查找交易记录
     *
     * @param currencyCode 货币码
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByCurrencyCode(String currencyCode, Pageable pageable);

    /**
     * description: 查询交易时间在time之后的交易记录
     *
     * @param time 交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByTransactionTimeAfter(Timestamp time, Pageable pageable);

    /**
     * description: 查询交易时间在time之前的交易记录
     *
     * @param time 交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByTransactionTimeBefore(Timestamp time, Pageable pageable);

    /**
     * description: 查询交易时间在beginTime至endTime之间的记录
     *
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByTransactionTimeBetween(Timestamp beginTime, Timestamp endTime, Pageable pageable);

    /**
     * description: 查询该银行卡交易时间在time之前的交易记录
     *
     * @param bankcardID 银行卡号
     * @param time 交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTransactionTimeBefore(String bankcardID, Timestamp time, Pageable pageable);

    /**
     * description: 查询该银行卡交易时间在time之后的交易记录
     *
     * @param bankcardID 银行卡号
     * @param time 交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTransactionTimeAfter(String bankcardID, Timestamp time, Pageable pageable);

    /**
     * description: 查询该银行卡交易时间在beginTime至endTime之间的记录
     *
     * @param bankcardID 银行卡号
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTransactionTimeBetween(String bankcardID, Timestamp beginTime, Timestamp endTime, Pageable pageable);

    /**
     * description: 根据银行卡号码、货币码，查询交易时间在time之前的交易记录
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param time         交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndCurrencyCodeAndTransactionTimeBefore(String bankcardID, String currencyCode, Timestamp time, Pageable pageable);

    /**
     * description: 根据银行卡号码、货币码，查询交易时间在time之后的交易记录
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param time         交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndCurrencyCodeAndTransactionTimeAfter(String bankcardID, String currencyCode, Timestamp time, Pageable pageable);

    /**
     * description: 根据银行卡号码、货币码，查询交易时间在beginTime和endTime之间的交易记录
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndCurrencyCodeAndTransactionTimeBetween(String bankcardID, String currencyCode, Timestamp beginTime, Timestamp endTime, Pageable pageable);

    /**
     * description: 通过银行卡号码、交易类型查询交易时间在time之前的交易记录
     *
     * @param bankcardID 银行卡号码
     * @param type       交易类型
     * @param time       交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTypeAndTransactionTimeBefore(String bankcardID, Integer type, Timestamp time, Pageable pageable);

    /**
     * description: 通过银行卡号码、交易类型查询交易时间在time之后的交易记录
     *
     * @param bankcardID 银行卡号码
     * @param type       交易类型
     * @param time       交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTypeAndTransactionTimeAfter(String bankcardID, Integer type, Timestamp time, Pageable pageable);

    /**
     * description: 通过银行卡号码、交易类型查询交易时间在beginTime和endTime之间的交易记录
     *
     * @param bankcardID 银行卡号码
     * @param type       交易类型
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTypeAndTransactionTimeBetween(String bankcardID, Integer type, Timestamp beginTime, Timestamp endTime, Pageable pageable);

    /**
     * description: 通过银行卡号码、交易货币、交易类型，查询交易时间在time之前的交易记录
     *
     * @param bankcardID   银行卡号码
     * @param type         交易类型
     * @param currencyCode 交易货币
     * @param time         交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeBefore(String bankcardID, Integer type, String currencyCode, Timestamp time, Pageable pageable);

    /**
     * description: 通过银行卡号码、交易货币、交易类型，查询交易时间在time之后的交易记录
     *
     * @param bankcardID   银行卡号码
     * @param type         交易类型
     * @param currencyCode 交易货币
     * @param time         交易时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeAfter(String bankcardID, Integer type, String currencyCode, Timestamp time, Pageable pageable);

    /**
     * description: 通过银行卡号码、交易货币、交易类型，查询交易时间在beginTime和endTime之间的交易记录
     *
     * @param bankcardID   银行卡号码
     * @param type         交易类型
     * @param currencyCode 交易货币
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param pageable 分页
     * @return org.springframework.data.domain.Page<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    Page<FxTransactionRecord> findByBankcardIdAndTypeAndCurrencyCodeAndTransactionTimeBetween(String bankcardID, Integer type, String currencyCode, Timestamp beginTime, Timestamp endTime, Pageable pageable);
}
