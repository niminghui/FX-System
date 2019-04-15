package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxUser;
import com.shiep.fxbankcard.repository.FxUserRepository;
import com.shiep.fxbankcard.service.IFxUserService;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/15 10:43
 * @description: IFxUserService实现类
 */
@Service
public class FxUserServiceImpl implements IFxUserService {
    @Autowired
    private FxUserRepository userRepository;

    @Override
    public List<FxUser> getByChineseName(String chineseName) {
        return userRepository.findByChinaName(chineseName);
    }

    @Override
    public FxUser getByIdCardNum(String idCardNum) {
        return userRepository.findByIdCardNum(idCardNum);
    }

    @Override
    public List<FxUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public FxUser insert(FxUser user) {
        user.setId(UuidTools.getUUID());
        return userRepository.save(user);
    }
}
