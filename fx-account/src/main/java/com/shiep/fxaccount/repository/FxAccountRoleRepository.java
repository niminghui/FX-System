package com.shiep.fxaccount.repository;

import com.shiep.fxaccount.entity.FxAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 10:46
 * @description: FxAccountRole的dao层
 */
public interface FxAccountRoleRepository extends JpaRepository<FxAccountRole,String> {
    /**
     * description: 通过账户名查找该账户具有的角色列表
     *
     * @param accountName 账户名
     * @return com.shiep.fxaccount.entity.FxAccountRole
     */
    List<FxAccountRole> findByAccountName(String accountName);

    /**
     * description: 通过角色名查找具有该角色的所有账户
     *
     * @param roleName 角色名
     * @return java.util.List<com.shiep.fxaccount.entity.FxAccountRole>
     */
    List<FxAccountRole> findByRoleName(String roleName);
}
