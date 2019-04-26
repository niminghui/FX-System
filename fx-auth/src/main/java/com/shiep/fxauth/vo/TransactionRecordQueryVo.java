package com.shiep.fxauth.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 倪明辉
 * @date: 2019/4/26 11:30
 * @description: 交易记录对查询条件视图对象
 */
@Data
public class TransactionRecordQueryVo implements Serializable {
    private static final long serialVersionUID = -1437807265162581920L;

    /**
     * description: 查询的交易记录的页数
     */
    private String page;

    /**
     * description: 交易记录开始时间
     */
    private String beginTime;

    /**
     * description: 交易记录结束时间
     */
    private String endTime;

    /**
     * description: 交易币种
     */
    private String currency;

    /**
     * description: 交易类型
     */
    private String type;
}
