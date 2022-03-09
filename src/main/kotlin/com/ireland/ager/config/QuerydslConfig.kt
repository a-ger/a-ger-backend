package com.ireland.ager.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * @Class : QuerydslConfig
 * @Description : QueryDsl 설정 클래스
 */
@Configuration
open class QuerydslConfig {
    @PersistenceContext
    private var entityManager: EntityManager? = null
    @Bean
    open fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}