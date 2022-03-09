package com.ireland.ager.main.service

import com.ireland.ager.account.service.AccountServiceImpl
import com.ireland.ager.main.repository.SearchRepository
import lombok.RequiredArgsConstructor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.transaction.Transactional


/**
 * @Class : RedisService
 * @Description : 메인 도메인에 대한 레디스 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
class SearchService {
    private val redisTemplate: RedisTemplate<String, String>? = null
    private val accountService: AccountServiceImpl? = null
    private val searchRepository: SearchRepository? = null

    /**
     * @Method : getSearchList
     * @Description : 최근 검색 리스트 조회
     * @Parameter : [accessToken]
     * @Return : List<String>
    </String> */
    fun getSearchList(accessToken: String?): List<String> {
        val accountByAccessToken = accountService!!.findAccountByAccessToken(accessToken)
        val key = "search::" + accountByAccessToken?.accountId
        val listOperations = redisTemplate!!.opsForList()
        return listOperations.range(key, 0, listOperations.size(key))
    }

    /**
     * @Method : getPopularSearchList
     * @Description : 인기 검색 리스트 조회
     * @Parameter : [accessToken]
     * @Return : List<String>
    </String> */
    @Cacheable(value = ["popular"])
    val popularSearchList: List<String?>?
        get() = searchRepository!!.findFirst5SearchesOrderByPopularDesc()

    /**
     * @Method : postKeyword
     * @Description : 사용자 별 검색 키워드 등록
     * @Parameter : [accessToken, keyword]
     * @Return : null
     */
    fun postKeyword(accessToken: String?, keyword: String?) {
        if (keyword == null) return
        //log.info("{}", keyword)
        val accountByAccessToken = accountService!!.findAccountByAccessToken(accessToken)
        val key = "search::" + accountByAccessToken.accountId
        val listOperations = redisTemplate!!.opsForList()
        for (pastKeyword in listOperations.range(key, 0, listOperations.size(key))) {
            if (pastKeyword.toString() == keyword) return
        }
        if (listOperations.size(key) < 5) {
            listOperations.rightPush(key, keyword)
        } else if (listOperations.size(key) == 5L) {
            listOperations.leftPop(key)
            listOperations.rightPush(key, keyword)
        } else {
            while (listOperations.size(key) >= 5) {
                listOperations.leftPop(key)
            }
            listOperations.rightPush(key, keyword)
        }
    }

    /**
     * @Method : deletePopularCacheFromRedis
     * @Description : 레디스의 인기 검색어를 매일 0시에 삭제
     * @Parameter : []
     * @Return : null
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @CacheEvict(value = ["popular"], allEntries = true)
    fun deletePopularCacheFromRedis() {
    }
}