package com.ireland.ager.main.repository

import com.ireland.ager.main.entity.QSearch
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * @Class : BoardRepositoryImpl
 * @Description : 게시판 도메인에 대한 레파지토리
 */
@Repository
@RequiredArgsConstructor
class SearchRepositoryImpl : SearchRepositoryCustom {
    private val queryFactory: JPAQueryFactory? = null
    override fun findFirst5SearchesOrderByPopularDesc(): List<String> {
        val now = LocalDateTime.now()
        return queryFactory
            .select(QSearch.search.keyword)
            .from(QSearch.search)
            .where(QSearch.search.createdAt.between(now.minusDays(1), now))
            .groupBy(QSearch.search.keyword)
            .orderBy(QSearch.search.keyword.count().desc())
            .limit(5)
            .fetch()
    }
}