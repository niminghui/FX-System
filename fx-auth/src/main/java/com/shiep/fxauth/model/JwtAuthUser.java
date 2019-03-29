package com.shiep.fxauth.model;

import com.shiep.fxauth.vo.FxAccountVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 13:55
 * @description: 实现UserDetails，封装账户信息，用于验证身份
 */
public class JwtAuthUser implements UserDetails {
    private String accountId;
    private String accountName;
    private String accountPwd;
    private String bankcardId;
    private List<String> roles;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * description: 通过FxAccountVo来创建JwtAuthUser
     *
     * @param accountVo
     * @return
     */
    public JwtAuthUser(FxAccountVo accountVo){
        this.accountId=accountVo.getAccountId();
        this.accountName=accountVo.getAccountName();
        this.accountPwd=accountVo.getAccountPwd();
        this.bankcardId=accountVo.getBankcardId();
        this.roles=accountVo.getRoles();
    }

    /**
     * description: 鉴权最重要方法，通过此方法来返回用户权限
     *
     * @param
     * @return java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (roles!=null) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.accountPwd;
    }

    @Override
    public String getUsername() {
        return this.accountName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtAuthUser{" +
                "id=" + accountId +
                ", name='" + accountName + '\'' +
                ", password='" + accountPwd + '\'' +
                ", authorities=" + roles +
                '}';
    }
}
