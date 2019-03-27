package com.shiep.fxauth.filter;

import com.alibaba.fastjson.JSON;
import com.shiep.fxauth.common.ResultEnum;
import com.shiep.fxauth.common.ResultVO;
import com.shiep.fxauth.model.JwtAuthUser;
import com.shiep.fxauth.model.LoginVO;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 14:54
 * @description: 登录认证过滤器
 */
public class JwtLoginAuthFilter extends UsernamePasswordAuthenticationFilter {
    private static final String REDIS_TOKEN_KEY="token";

    private AuthenticationManager authenticationManager;

    private ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();

    public JwtLoginAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置该过滤器地址
        super.setFilterProcessesUrl("/auth/login");
    }

    /**
     * description: 登录验证
     *
     * @param request 请求
     * @param response 响应
     * @return org.springframework.security.core.Authentication
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        System.out.println("/auth/login");
        LoginVO loginVO = new LoginVO();
        loginVO.setAccount_name(request.getParameter("name"));
        loginVO.setAccount_pwd(request.getParameter("password"));
        loginVO.setRememberMe(Boolean.parseBoolean(request.getParameter("rememberMe")));
        System.out.println(loginVO.toString());
        rememberMe.set(loginVO.getRememberMe());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginVO.getAccount_name(), loginVO.getAccount_pwd(), new ArrayList<>())
        );
    }

    /**
     * description: 登录验证成功后调用，验证成功后将生成Token，并重定向到用户主页home
     * 与AuthenticationSuccessHandler作用相同
     *
     * @param request 请求
     * @param response 响应
     * @param chain 过滤链
     * @param authResult 认证结果
     * @return void
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象，这里是JwtAuthUser
        JwtAuthUser jwtUser = (JwtAuthUser) authResult.getPrincipal();
        System.out.println("JwtAuthUser:" + jwtUser.toString());
        boolean isRemember = rememberMe.get();
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            roles.add(authority.getAuthority());
        }
        System.out.println("roles:"+roles);
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), roles, isRemember);
        System.out.println("token:"+token);
        // 将Token存入Redis
        RedisUtils.put(REDIS_TOKEN_KEY,jwtUser.getUsername(),token);
        System.out.println("redis token:"+ RedisUtils.getHashValue(REDIS_TOKEN_KEY,jwtUser.getUsername()));
//        redisTemplate.opsForHash().put("token",jwtUser.getUsername(),token);
//        System.out.println("Redis Token:"+redisTemplate.opsForHash().get("token",jwtUser.getUsername()));
        // 重定向无法设置header,这里设置header只能设置到/auth/login界面的header
        //response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);

        // 登录成功重定向到home界面==>(可先跳转到控制层，在控制层设置（Token存到Redis等等），在跳转到页面)
        // 这里先采用参数传递
        response.sendRedirect("/home?token="+token);
    }

    /**
     * description: 登录验证失败后调用，这里直接Json返回，实际上可以重定向到错误界面等
     * 与AuthenticationFailureHandler作用相同
     *
     * @param request 请求
     * @param response 响应
     * @param failed 认证异常
     * @return void
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(ResultVO.result(ResultEnum.USER_LOGIN_FAILED,false)));
    }
}
