package com.shiep.fxauth.filter;

import com.shiep.fxauth.common.HttpStatusEnum;
import com.shiep.fxauth.vo.ResultVo;
import com.shiep.fxauth.utils.CookieUtils;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 15:07
 * @description: Basic认证过滤器
 */
public class JwtPreAuthFilter extends BasicAuthenticationFilter {
    private final static String[] IGNORED_URL = {"/login", "/register", "/css", "/js", "/img"};
    public JwtPreAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * description: 从request的header部分读取Token
     *
     * @param request 请求
     * @param response 响应
     * @param chain 过滤链
     * @return void
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        //String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 从cookie中取Token
        Cookie cookie = CookieUtils.getCookie(request, "token");
        if (cookie == null) {
            chain.doFilter(request, response);
            return;
        }
        String tokenHeader = URLDecoder.decode(cookie.getValue());
        String uri = request.getRequestURI();
        for (String str : IGNORED_URL) {
            if (uri.startsWith(str)) {
                chain.doFilter(request, response);
                return;
            }
        }
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 对其他情况进行解析Token，并且设置认证信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(tokenHeader, request, response);
        if (usernamePasswordAuthenticationToken == null){
            // 此时应该是用户Token过期，将跳转到登录界面
            Map<String, Object> map = ResultVo.result(HttpStatusEnum.USER_LOGIN_OVERDUE, false);
            request.setAttribute("code",map.get("code"));
            request.setAttribute("msg",map.get("messageCN"));
            request.getRequestDispatcher("/error").forward(request,response);
        }
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        super.doFilterInternal(request, response, chain);
    }

    /**
     * description: 读取Token信息，创建UsernamePasswordAuthenticationToken对象
     *
     * @param tokenHeader Token字符串
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response) throws IOException, ServletException {
        //解析Token时将“Bearer ”前缀去掉
        String userToken = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        // 如果Token过期
        if (JwtTokenUtils.isExpiration(userToken)) {
            Map<String, Object> map = ResultVo.result(HttpStatusEnum.USER_LOGIN_OVERDUE, false);
            request.setAttribute("code", map.get("code"));
            request.setAttribute("msg", map.get("messageCN"));
            request.getRequestDispatcher("/error").forward(request, response);
        }
        String username = JwtTokenUtils.getUsername(userToken);
        String token = RedisUtils.hGet("token",username).toString();
        // 如果用户传来的Token跟Redis中存储的Token匹配的话
        if(userToken.equals(token)){
            List<String> roles = JwtTokenUtils.getUserRole(token);
            Collection<GrantedAuthority> authorities = new HashSet<>();
            if (roles != null) {
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
            }
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        }
        return null;
    }
}
