package com.ireland.ager.config

import com.ireland.ager.config.interceptor.KakaoAuthenticationInterceptor
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @Class : KakaoAuthenticationConfig
 * @Description : 인터셉터 설정 클래스
 */
@Configuration
@RequiredArgsConstructor
open class KakaoAuthenticationConfig : WebMvcConfigurer {
    private var kakaoAuthenticationInterceptor: KakaoAuthenticationInterceptor? = null
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(kakaoAuthenticationInterceptor)
            .addPathPatterns("/**")
        super.addInterceptors(registry)
    }
}