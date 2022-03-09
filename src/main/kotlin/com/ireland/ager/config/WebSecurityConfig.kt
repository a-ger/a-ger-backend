package com.ireland.ager.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*

/**
 * @Class : WebSecurityConfig
 * @Description : 스프링 시큐리티 설정 클래스
 */
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    /**
     * @Method : configure
     * @Description : csrf 해제, 스프링 시큐리티 설정
     * @Parameter : [http]
     * @Return : null
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .anyRequest().permitAll()
    }

    /**
     * @Method : corsConfigurationSource
     * @Description : 스프링 시큐리티 설정
     * @Parameter : []
     * @Return : CorsConfigurationSource
     */
    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedMethod("PATCH")
        configuration.addAllowedMethod("HEAD")
        configuration.addAllowedMethod("GET")
        configuration.addAllowedMethod("PUT")
        configuration.addAllowedMethod("POST")
        configuration.addAllowedMethod("DELETE")
        configuration.addAllowedMethod("OPTIONS")
        configuration.allowedMethods = Arrays.asList("HEAD", "POST", "PUT", "PATCH", "GET", "DELETE", "OPTIONS")
        configuration.addAllowedHeader("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**")
    }
}