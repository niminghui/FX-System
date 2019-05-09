package com.shiep.fxaccount.service;

import com.shiep.fxaccount.entity.FxRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 11:02
 * @description: FxRole服务接口
 */
@Transactional(rollbackFor = Exception.class)
public interface IFxRoleService {
    /**
     * description: 通过角色名创建FxRole
     *
     * @param roleName 角色名
     * @return com.shiep.fxaccount.entity.FxRole
     */
    FxRole create(String roleName);

    /**
     * description: 通过角色名和描述创建FxRole
     *
     * @param roleName 角色名
     * @param describe 角色描述
     * @return com.shiep.fxaccount.entity.FxRole
     */
    FxRole create(String roleName,String describe);

    /**
     * description: 得到所有角色
     *
     * @param
     * @return java.util.List<com.shiep.fxaccount.entity.FxRole>
     */
    List<FxRole> getAllRoles();
}
