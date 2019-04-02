package com.shiep.fxauth.filter;

import com.shiep.fxauth.common.HttpStatusEnum;
import com.shiep.fxauth.common.ResultVO;
import com.shiep.fxauth.model.JwtAuthUser;
import com.shiep.fxauth.model.LoginVO;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 14:54
 * @description: 登录认证过滤器
 */
public class JwtLoginAuthFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtLoginAuthFilter.class);
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
        //LoginVO loginVO = (LoginVO) request.getAttribute("loginUser");
        LoginVO loginVO = new LoginVO();
        loginVO.setAccountName(request.getParameter("name"));
        loginVO.setPassword(request.getParameter("password"));
        loginVO.setRememberMe(Boolean.parseBoolean(request.getParameter("rememberMe")));
        logger.info(loginVO.toString());
        rememberMe.set(loginVO.getRememberMe());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginVO.getAccountName(), loginVO.getPassword(), new ArrayList<>())
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
        logger.info(jwtUser.toString());
        boolean isRemember = rememberMe.get();
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            roles.add(authority.getAuthority());
        }
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), roles, isRemember);
        logger.info("token:"+token);
        // 将Token存入Redis
        RedisUtils.hPut(REDIS_TOKEN_KEY,jwtUser.getUsername(),token);
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
        request.getRequestDispatcher("/home").forward(request,response);
        //response.sendRedirect("/home?token="+token);
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
        Map<String, Object> map = ResultVO.result(HttpStatusEnum.USER_LOGIN_FAILED,false);
        request.setAttribute("code",map.get("code"));
        request.setAttribute("msg",map.get("messageCN"));
        request.getRequestDispatcher("/error").forward(request,response);
    }
}
