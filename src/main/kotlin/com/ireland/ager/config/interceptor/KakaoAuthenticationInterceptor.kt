package com.ireland.ager.config.interceptor

import com.ireland.ager.account.service.AuthServiceImpl
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.apache.http.HttpHeaders
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.PatternMatchUtils
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Class : KakaoAuthenticationInterceptor
 * @Description : 카카오 인증 인터셉터
 */
@Component
@Slf4j
@RequiredArgsConstructor
class KakaoAuthenticationInterceptor : HandlerInterceptor {
    private val authService: AuthServiceImpl? = null

    /**
     * @Method : preHandle implements HandlerInterceptor
     * @Description : 토큰 유효성 검사
     * @Parameter : [request, response, handler]
     * @Return : boolean
     */
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val log = LoggerFactory.getLogger(javaClass)
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)
        val requestUrl = request.requestURI
        log.info("URL:{}", requestUrl)
        if (!PatternMatchUtils.simpleMatch(excludeList, requestUrl)) {
            log.info("Not Match")
            authService!!.isValidToken(token)
        }
        return super.preHandle(request, response, handler)
    }

    companion object {
        private val excludeList = arrayOf(
            "/api/account/login-url",
            "/api/account/login-web",
            "/api/account/login-web/*",
            "/api/account/login-web/**",
            "/api/account/login",
            "/api/account/token/**",
            "/favicon.ico/**",
            "/favicon.ico",
            "/kafka/*",
            "/kafka/**",
            "/socket.io/*",
            "/swagger-ui/**",
            "/swagger-ui/*",
            "/swagger-resources/*",
            "/swagger-resources/**",
            "/swagger-resources",
            "/v2/api-docs"
        )
    }
}