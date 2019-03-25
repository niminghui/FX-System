package com.shiep.fxauth.config;

import com.shiep.fxauth.filter.JwtLoginAuthFilter;
import com.shiep.fxauth.filter.JwtPreAuthFilter;
import com.shiep.fxauth.handler.FxLogoutSuccessHandler;
import com.shiep.fxauth.handler.UnAuthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author: 倪明辉
 * @date: 2019/3/6 16:24
 * @description:
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    // 加密器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * description: 加载userDetailsService，用于从account微服务中取用户信息
     *
     * @param auth
     * @return void
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * description: http细节
     *
     * @param http
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启跨域资源共享
        http.cors()
                .and()
                // 关闭csrf
                .csrf().disable()
                // 关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().authenticationEntryPoint(new UnAuthorizedEntryPoint())
                .and()
                .authorizeRequests()
                // 需要角色为ADMIN才能操作该资源
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                // 账户微服务和银行卡微服务需要认证的用户才能访问
                .antMatchers("/b/**","/a/**").authenticated()
                // 其他都放行了
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()//默认注销行为为logout
                .logoutSuccessHandler(new FxLogoutSuccessHandler())
                .and()
                // 添加到过滤链中
                // 先是UsernamePasswordAuthenticationFilter用于login校验
                .addFilter(new JwtLoginAuthFilter(authenticationManager()))
                // 再通过OncePerRequestFilter，对其他请求过滤
                .addFilter(new JwtPreAuthFilter(authenticationManager()));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
