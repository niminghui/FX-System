package com.shiep.fxbankcard.api.model;

import lombok.Data;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 15:57
 * @description: 人民币牌价model类
 */
@Data
public class CNYRateModel {
    /**
     * description: 发布日期
     */
    private String date;
    /**
     * description: 发布时间
     */
    private String time;
    /**
     * description: 货币名称
     */
    private String currencyName;
    /**
     * description: 货币符号
     */
    private String currencyCode;
    /**
     * description: 现汇买入价
     */
    private String fBuyPri;
    /**
     * description: 现汇卖出价
     */
    private String fSellPri;
    /**
     * description: 银行折算价（中间价）
     */
    private String bankConversionPri;
}
