package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/22 13:36
 * @description: 用户信息服务端点
 */
@RequestMapping("/user")
public interface IUserInfoService {

    /**
     * description: 通过中文名查找用户
     *
     * @param chineseName 中文名
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @GetMapping("/chineseName/{chineseName}")
    List<FxUser> getByChineseName(@PathVariable("chineseName") String chineseName);

    /**
     * description: 通过身份证号码查找该用户
     *
     * @param idNumber 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @GetMapping("/idNumber/{idNumber}")
    FxUser getByIdCardNum(@PathVariable("idNumber") String idNumber);

    /**
     * description: 通过邮箱查找该用户
     *
     * @param email 邮箱
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @GetMapping("/email/{email}")
    FxUser getByEmail(@PathVariable("email") String email);

    /**
     * description: 返回所有用户信息
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxUser>
     */
    @GetMapping
    List<FxUser> getAll();

    /**
     * description: 新建用户信息
     *
     * @param user FxUser
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    @PostMapping
    FxUser createFxUser(FxUser user);
}
