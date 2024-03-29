package com.shiep.fxaccount.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author: 倪明辉
 * @date: 2019/5/7 17:06
 * @description: Druid连接池配置(http://localhost:8001/druid/login.html)
 */
@Configuration
@PropertySource(value = "classpath:application.yml")
public class DruidConfig {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    /**
     * description: 配置数据域
     *
     * @param
     * @return javax.sql.DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * description: 注册一个StatViewServlet
     *
     * @param
     * @return org.springframework.boot.web.servlet.ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        // 通过ServletRegistrationBean类进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加初始化参数initParams：白名单：
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        // servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        // 登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", USERNAME);
        servletRegistrationBean.addInitParameter("loginPassword", PASSWORD);
        // 是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * description: druid过滤器，注册一个filterRegistrationBean
     *
     * @param
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}

