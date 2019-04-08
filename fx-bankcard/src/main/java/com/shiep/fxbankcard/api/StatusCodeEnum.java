package com.shiep.fxbankcard.api;

import lombok.Getter;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 16:10
 * @description: 状态码枚举类
 */
@Getter
public enum StatusCodeEnum {
    /**
     * description: 校验身份证api返回的状态码
     */
    SUCCESS(200, "成功"),
    ID_NUMBER_INVALID(201, "身份证号无效"),
    ID_NUMBER_WRONG_FORMAT(401, "身份证号格式错误"),

    /**
     * description: 外汇汇率api返回的状态码
     */
    QUERY_NO_RESULT(202301, "查询不到结果"),
    KEY_WRONG(10001, "错误的请求KEY"),
    KEY_NO_PERMISSION(10002, "该KEY无请求权限"),
    KEY_EXPIRED(10003, "KEY过期"),
    OPENID_WRONG(10004, "错误的OPENID"),
    AUTH_WRONG(10005, "应用未审核"),
    UNKNOWN_REQUEST_SOURCE(10007, "未知的请求源"),
    IP_BANNED(10008, "被禁止的IP"),
    KEY_BANNED(10009, "被禁止的KEY"),
    IP_REQUEST_EXCEEDS_LIMITT(10011, "当前IP请求超过限制"),
    REQUEST_EXCEEDS_LIMITT(10012, "请求超过次数限制"),
    INTERFACE_MAINTAIN(10020, "接口维护"),
    INTERFACE_DISABLED(10021, "接口停用"),

    OLD_KEY_WRONG(101, "错误的请求KEY"),
    OLD_KEY_NO_PERMISSION(102, "该KEY无请求权限"),
    OLD_KEY_EXPIRED(103, "KEY过期"),
    OLD_OPENID_WRONG(104, "错误的OPENID"),
    OLD_AUTH_WRONG(105, "应用未审核"),
    OLD_UNKNOWN_REQUEST_SOURCE(107, "未知的请求源"),
    OLD_IP_BANNED(108, "被禁止的IP"),
    OLD_KEY_BANNED(109, "被禁止的KEY"),
    OLD_IP_REQUEST_EXCEEDS_LIMITT(111, "当前IP请求超过限制"),
    OLD_REQUEST_EXCEEDS_LIMITT(112, "请求超过次数限制"),
    OLD_INTERFACE_MAINTAIN(120, "接口维护"),
    OLD_INTERFACE_DISABLED(121, "接口停用");



    private Integer code;

    private String message;

    StatusCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * description: 通过code返回message
     *
     * @param code
     * @return com.shiep.fxbankcard.api.StatusCodeEnum
     */
    public static StatusCodeEnum parse(int code) {
        StatusCodeEnum[] values = values();
        for (StatusCodeEnum value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new RuntimeException("Unknown code of StatusCodeEnum");
    }

    /**
     * description: 通过code返回message
     *
     * @param code
     * @return java.lang.String
     */
    public static String getMsgByCode(int code) {
        return parse(code).getMessage();
    }
}
