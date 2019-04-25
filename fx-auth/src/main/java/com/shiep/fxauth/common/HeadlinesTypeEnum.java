package com.shiep.fxauth.common;

import lombok.Getter;

/**
 * @author: 倪明辉
 * @date: 2019/4/25 10:38
 * @description: 新闻类型枚举类
 */
@Getter
public enum HeadlinesTypeEnum {
    /**
     * description: 新闻头条API支持的10种新闻类型
     */
    TYPE_TOP("top", "头条"),
    TYPE_SOCIETY("shehui", "社会"),
    TYPE_DOMESTIC("guonei", "国内"),
    TYPE_INTERNATIONAL("guoji", "国际"),
    TYPE_RECREATION("yule", "娱乐"),
    TYPE_SPORTS("tiyu", "体育"),
    TYPE_MILITARY("junshi", "军事"),
    TYPE_SCIENCE("keji", "科技"),
    TYPE_FINANCE("caijing", "财经"),
    TYPE_FASHION("shishang", "时尚");

    /**
     * description: 新闻头条API请求的类型码
     */
    private String code;
    /**
     * description: 新闻头条类型的中文描述
     */
    private String name;

    /**
     * description: 构造方法
     *
     * @param code 新闻头条API请求的类型码
     * @param name 新闻头条类型的中文描述
     * @return
     */
    HeadlinesTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * description: 通过code返回HeadlinesTypeEnum
     *
     * @param code 新闻头条API请求的类型码
     * @return com.shiep.fxauth.common.HeadlinesTypeEnum
     */
    public static HeadlinesTypeEnum parse(Integer code) {
        HeadlinesTypeEnum[] values = values();
        for (HeadlinesTypeEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new RuntimeException("Unknown code of CurrencyEnum");
    }
}
