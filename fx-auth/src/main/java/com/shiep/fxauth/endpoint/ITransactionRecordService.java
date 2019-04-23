package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxTransactionRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 16:01
 * @description: 交易记录服务端点
 */
@RequestMapping("/record")
public interface ITransactionRecordService {

    /**
     * description: 通过交易类型查找所有的交易记录
     *
     * @param type 交易类型
     * @return java.util.List<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    @GetMapping("/type")
    List<FxTransactionRecord> findByType(@RequestParam("type") Integer type);

    /**
     * description: 通过货币码查找交易记录
     *
     * @param currencyCode 货币码
     * @return java.util.List<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    @GetMapping("/currency")
    List<FxTransactionRecord> findByCurrency(@RequestParam("currencyCode") String currencyCode);

    /**
     * description: 以0，1表示time是否有值。00返回null；10查询交易时间在beginTime之前的交易记录；
     * 01查询交易时间在endTime之后的交易记录；11查询交易时间在beginTime和endTime之间的交易记录。
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return java.util.List<com.shiep.fxbankcard.entity.FxTransactionRecord>
     */
    @GetMapping("/time")
    List<FxTransactionRecord> findByTransactionTime(@RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                                    @RequestParam(value = "endTime", required = false) Timestamp endTime);

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
    @GetMapping("/bankcard")
    List<FxTransactionRecord> query(@RequestParam("bankcardID") String bankcardID,
                                    @RequestParam(value = "currencyCode", required = false) String currencyCode,
                                    @RequestParam(value = "type", required = false) Integer type,
                                    @RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                    @RequestParam(value = "endTime", required = false) Timestamp endTime);

    /**
     * description: 增加一条交易记录
     *
     * @param transactionRecord 交易记录
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    @PostMapping
    FxTransactionRecord create(FxTransactionRecord transactionRecord);
}
