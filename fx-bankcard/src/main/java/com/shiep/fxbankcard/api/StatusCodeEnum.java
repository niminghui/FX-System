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
     *
     * @param null
     * @return
     */
    SUCCESS(200,"成功"),
    ID_NUMBER_INVALID(201,"身份证号无效"),
    ID_NUMBER_WRONG_FORMAT(401,"身份证号格式错误"),
    ;

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
    public static StatusCodeEnum parse(int code){
        StatusCodeEnum[] values = values();
        for (StatusCodeEnum value : values) {
            if(value.getCode() == code){
                return value;
            }
        }
        throw  new RuntimeException("Unknown code of StatusCodeEnum");
    }
}
