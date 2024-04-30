package com.encrypt.hospital.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 暂时添加SecurityConfig，用于测试前后端交互，防止重定向到/login，后面可以删除
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll() // 允许所有请求
                .and()
                .csrf().disable(); // 禁用 CSRF 保护，仅推荐在测试环境中这么做
    }
}
