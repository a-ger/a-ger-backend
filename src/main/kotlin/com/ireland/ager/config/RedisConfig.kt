package com.ireland.ager.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

/**
 * @Class : RedisConfig
 * @Description : 레디스 설정 클래스
 */
@Configuration
open class RedisConfig : CachingConfigurerSupport() {
    @Value("\${spring.redis.host}")
    private val host: String? = null

    @Value("\${spring.redis.timeout}")
    private val timeout: Long? = null

    /**
     * @Method : lettuceConnectionFactory
     * @Description : 레튜스 설정 빈 등록
     * @Parameter : []
     * @Return : LettuceConnectionFactory
     */
    @Bean
    open fun lettuceConnectionFactory(): LettuceConnectionFactory {
        val lettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ZERO)
            .shutdownTimeout(Duration.ZERO)
            .build()
        val redisStandaloneConfiguration = RedisStandaloneConfiguration(host, 6379)
        return LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration)
    }

    @Bean
    open
            /**
             * @Method : redisTemplate
             * @Description : 레디스 템플릿 빈 등록
             * @Parameter : []
             * @Return : RedisTemplate
             */
    fun redisTemplate(): RedisTemplate<*, *> {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        template.connectionFactory = lettuceConnectionFactory()
        return template
    }

    /**
     * @Method : cacheManager
     * @Description : 캐시 매니저 빈 등록
     * @Parameter : []
     * @Return : CacheManager
     */
    @Bean
    override fun cacheManager(): CacheManager {
        val builder = RedisCacheManagerBuilder
            .fromConnectionFactory(lettuceConnectionFactory())
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofDays(timeout!!))
        builder.cacheDefaults(configuration)
        return builder.build()
    }
}