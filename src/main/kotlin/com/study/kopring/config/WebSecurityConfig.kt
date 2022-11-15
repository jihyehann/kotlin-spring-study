package com.study.kopring.config

import com.study.kopring.controller.ExceptionHandler
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


@EnableWebSecurity
class WebSecurityConfig(
    private val jwtFilter: JwtFilter,
    private val exceptionHandler: ExceptionHandler
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()

        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable() // 폼 로그인 비활성화
            .httpBasic().disable() // Http Basic Auth 인증 비활성화

    }
}