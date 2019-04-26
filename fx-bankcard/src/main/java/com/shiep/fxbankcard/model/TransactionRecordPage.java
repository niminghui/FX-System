package com.shiep.fxbankcard.model;

import com.shiep.fxbankcard.entity.FxTransactionRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/26 9:39
 * @description: 交易记录分页类
 */
@Data
public class TransactionRecordPage implements Serializable {
    private static final long serialVersionUID = -378936511086317671L;

    /**
     * description: 总记录数
     */
    private Long size;

    /**
     * description: 当前页数
     */
    private Integer pageIndex;

    /**
     * description: 总页数
     */
    private Integer pageSize;

    /**
     * description: 当前页面内容
     */
    private List<FxTransactionRecord> pageContent;

    /**
     * description: 当前页面记录数
     */
    private Integer currencyPageSize;
}
