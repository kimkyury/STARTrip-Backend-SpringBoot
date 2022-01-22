package com.startrip.codebase.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // JWT 사용으로 비활성화

                .authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/api/user/signup").permitAll()

                .anyRequest().authenticated();
    }
}