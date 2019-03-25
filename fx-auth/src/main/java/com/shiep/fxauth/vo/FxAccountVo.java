package com.shiep.fxauth.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 11:29
 * @description: 账户视图类
 */
@Data
public class FxAccountVo {
    private String accountId;
    private String accountName;
    private String accountPwd;
    private String bankcardId;
    private List<String> roles;
}
