package com.shiep.fxaccount.service.impl;

import com.shiep.fxaccount.entity.FxRole;
import com.shiep.fxaccount.repository.FxRoleRepository;
import com.shiep.fxaccount.service.IFxRoleService;
import com.shiep.fxaccount.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 13:32
 * @description: IFxRoleService的实现类
 */
@Service
public class FxRoleServiceImpl implements IFxRoleService {

    @Autowired
    FxRoleRepository roleRepository;

    @Override
    public FxRole create(String roleName) {
        return create(roleName,"");
    }

    @Override
    public FxRole create(String roleName, String describe) {
        FxRole role = new FxRole();
        role.setId(UuidTools.getUUID());
        role.setName(roleName);
        role.setDescribe(describe);
        return roleRepository.save(role);
    }

    @Override
    public List<FxRole> getAllRoles() {
        return roleRepository.findAll();
    }
}
