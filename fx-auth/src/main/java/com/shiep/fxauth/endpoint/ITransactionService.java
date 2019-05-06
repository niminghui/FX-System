package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxTransactionRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author: 倪明辉
 * @date: 2019/4/30 16:59
 * @description: 交易服务端点
 */
@RequestMapping("/transaction")
public interface ITransactionService {
    /**
     * description: 计算活期利息
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 币种
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    @PostMapping("/calculate/interest")
    FxTransactionRecord calculateCurrentInterest(@RequestParam("bankcardID") String bankcardID,
                                                 @RequestParam("currencyCode") String currencyCode);

    /**
     * description: 外汇存款
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 币种
     * @param money        金额
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    @PostMapping("/deposit")
    FxTransactionRecord deposit(@RequestParam("bankcardID") String bankcardID,
                                @RequestParam("currencyCode") String currencyCode,
                                @RequestParam("money") BigDecimal money);

    /**
     * description: 外汇转账
     *
     * @param bankcardID      银行卡号码
     * @param currencyCode    币种
     * @param money           金额
     * @param otherBankcardID 交易方
     * @return com.shiep.fxbankcard.entity.FxTransactionRecord
     */
    @PostMapping("/transfer")
    FxTransactionRecord transfer(@RequestParam("bankcardID") String bankcardID,
                                 @RequestParam("currencyCode") String currencyCode,
                                 @RequestParam("money") BigDecimal money,
                                 @RequestParam("otherBankcardID") String otherBankcardID);

    /**
     * description: 外汇买卖（外币之间的交易）
     *
     * @param bankcardID        银行卡号码
     * @param basicCurrency     基本货币
     * @param secondaryCurrency 二级货币
     * @param money             买卖金额
     * @param rate              汇率(交易单位：1)
     * @param buy               true为买入，false为卖出
     * @return java.lang.Boolean
     */
    @PostMapping("/fx/trading")
    Boolean foreignExchangeTrading(@RequestParam("bankcardID") String bankcardID,
                                   @RequestParam("basicCurrency") String basicCurrency,
                                   @RequestParam("secondaryCurrency") String secondaryCurrency,
                                   @RequestParam("money") BigDecimal money,
                                   @RequestParam("rate") BigDecimal rate,
                                   @RequestParam("buy") Boolean buy);

    /**
     * description: 人民币结汇（外汇换人民币）
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 外汇币种
     * @param money        金额
     * @param rate         汇率（交易单位：100）
     * @return java.lang.Boolean
     */
    @PostMapping("/fx/settlement")
    Boolean foreignExchangeSettlement(@RequestParam("bankcardID") String bankcardID,
                                      @RequestParam("currencyCode") String currencyCode,
                                      @RequestParam("money") BigDecimal money,
                                      @RequestParam("rate") BigDecimal rate);

    /**
     * description: 人民币购汇（人民币购买外汇）
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 外汇币种
     * @param money        金额
     * @param rate         汇率（交易单位：100）
     * @return java.lang.Boolean
     */
    @PostMapping("/fx/purchase")
    Boolean foreignExchangePurchase(@RequestParam("bankcardID") String bankcardID,
                                    @RequestParam("currencyCode") String currencyCode,
                                    @RequestParam("money") BigDecimal money,
                                    @RequestParam("rate") BigDecimal rate);
}
