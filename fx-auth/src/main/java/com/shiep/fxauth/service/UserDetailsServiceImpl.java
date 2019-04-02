package com.shiep.fxauth.service;

import com.shiep.fxauth.endpoint.IAccountService;
import com.shiep.fxauth.model.JwtAuthUser;
import com.shiep.fxauth.vo.FxAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 14:47
 * @description: 实现UserDetailsService，从Account微服务加载账户信息==》账户名、密码及角色列表
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @SuppressWarnings("all")
    @Autowired
    private IAccountService accountService;

    /**
     * description: 通过用户名从Account微服务中读取该用户账户信息及权限信息
     *
     * @param userName 账户名
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        FxAccountVo accountVo = accountService.getAccountVo(userName);
        if(accountVo==null){
            // 实际当用户不存在时，应该页面显示错误信息，并跳转到登录界面
            throw new UsernameNotFoundException("该用户不存在！");
        }
        return new JwtAuthUser(accountVo);
    }
}
