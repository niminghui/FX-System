package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.common.TransactionRecordPage;
import com.shiep.fxauth.model.FxTransactionRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

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
     * @param page 页数
     * @param size 页面大小
     * @return com.shiep.fxauth.common.TransactionRecordPage
     */
    @GetMapping("/type")
    TransactionRecordPage findByType(@RequestParam("type") Integer type,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size);

    /**
     * description: 通过货币码查找交易记录
     *
     * @param currencyCode 货币码
     * @param page 页数
     * @param size 页面大小
     * @return com.shiep.fxauth.common.TransactionRecordPage
     */
    @GetMapping("/currency")
    TransactionRecordPage findByCurrency(@RequestParam("currencyCode") String currencyCode,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size);

    /**
     * description: 以0，1表示time是否有值。00返回null；10查询交易时间在beginTime之前的交易记录；
     * 01查询交易时间在endTime之后的交易记录；11查询交易时间在beginTime和endTime之间的交易记录。
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page 页数
     * @param size 页面大小
     * @return com.shiep.fxauth.common.TransactionRecordPage
     */
    @GetMapping("/time")
    TransactionRecordPage findByTransactionTime(@RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                                @RequestParam(value = "endTime", required = false) Timestamp endTime,
                                                @RequestParam("page") Integer page,
                                                @RequestParam("size") Integer size);

    /**
     * description: 银行卡号码不能为空，其他四个参数可为为空。多功能查询该银行卡的交易记录。
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param type         交易类型
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page 页数
     * @param size 页面大小
     * @return com.shiep.fxauth.common.TransactionRecordPage
     */
    @GetMapping("/bankcard")
    TransactionRecordPage query(@RequestParam("bankcardID") String bankcardID,
                                @RequestParam(value = "currencyCode", required = false) String currencyCode,
                                @RequestParam(value = "type", required = false) Integer type,
                                @RequestParam(value = "beginTime", required = false) Timestamp beginTime,
                                @RequestParam(value = "endTime", required = false) Timestamp endTime,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size);

    /**
     * description: 增加一条交易记录
     *
     * @param transactionRecord 交易记录
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    @PostMapping
    FxTransactionRecord create(FxTransactionRecord transactionRecord);
}
