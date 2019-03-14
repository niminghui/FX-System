package com.shiep.fxaccount.repository;

import com.shiep.fxaccount.entity.FxRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 10:44
 * @description: FxRole的dao层
 */
public interface FxRoleRepository extends JpaRepository<FxRole,String> {
    /**
     * description: 通过角色名查找FxRole
     *
     * @param roleName
     * @return com.shiep.fxaccount.entity.FxRole
     */
    FxRole findByName(String roleName);
}
