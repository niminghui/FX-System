package com.shiep.fxbankcard.api.model;

import lombok.Data;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 17:08
 * @description: 外汇汇率model类
 */
@Data
public class FxRateModel {
    /**
     * description: 货币对标准符号
     */
    private String currencyPairsCode;
    /**
     * description: 货币对中文名
     */
    private String currencyPairsName;
    /**
     * description: 发布日期
     */
    private String date;
    /**
     * description: 发布时间
     */
    private String time;
    /**
     * description: 最新价格
     */
    private String closePri;
    /**
     * description: 涨跌百分比
     */
    private String diffPer;
    /**
     * description: 涨跌金额
     */
    private String diffAmo;
    /**
     * description: 开盘价
     */
    private String openPri;
    /**
     * description: 最高价
     */
    private String highPic;
    /**
     * description: 最低价
     */
    private String lowPic;
    /**
     * description: 震幅百分比
     */
    private String range;
    /**
     * description: 买入价
     */
    private String buyPic;
    /**
     * description: 卖出价
     */
    private String sellPic;
    /**
     * description: 涨跌颜色
     */
    private String color;
    /**
     * description: 昨收价
     */
    private String yesPic;
}
