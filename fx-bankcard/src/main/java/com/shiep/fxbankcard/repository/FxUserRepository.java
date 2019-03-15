package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 10:29
 * @description: FxUser的数据访问层
 */
@Repository
public interface FxUserRepository extends JpaRepository<FxUser, String> {
    /**
     * description: 通过中文名查找用户
     *
     * @param chinaName
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    List<FxUser> findByChinaName(String chinaName);

    /**
     * description: 通过身份证号码查找该用户
     *
     * @param idCardNum
     * @return com.shiep.fxbankcard.entity.FxUser
     */
    FxUser findByIdCardNum(String idCardNum);
}
