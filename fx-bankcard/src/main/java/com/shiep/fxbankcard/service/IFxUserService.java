package com.shiep.fxbankcard.service;

import com.shiep.fxbankcard.entity.FxUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/15 10:37
 * @description: 用户服务接口
 * 对于用户信息，无需删除（数据），不能修改（已经认证过了）
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxUserService {
    /**
     * description: 通过中文名查找用户
     *
     * @param chineseName 中文名
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    List<FxUser> getByChineseName(String chineseName);

    /**
     * description: 通过身份证号码查找该用户
     *
     * @param idCardNum 银行卡号码
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    FxUser getByIdCardNum(String idCardNum);

    /**
     * description: 返回所有用户信息
     *
     * @param
     * @return java.util.List<com.shiep.fxbankcard.entity.FxUser>
     */
    List<FxUser> getAll();

    /**
     * description: 新建用户信息
     *
     * @param user FxUser
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    FxUser insert(FxUser user);
}
