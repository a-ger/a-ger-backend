package com.ireland.ager.account.service

import com.ireland.ager.account.dto.request.AccountUpdateRequest
import com.ireland.ager.account.dto.response.MyAccountResponse
import com.ireland.ager.account.entity.Account
import com.ireland.ager.account.exception.UnAuthorizedAccessException
import com.ireland.ager.account.repository.AccountRepository
import com.ireland.ager.main.exception.NotFoundException
import com.ireland.ager.main.service.UploadServiceImpl
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.function.Supplier

/**
 * @Class : AccountServiceImpl
 * @Description : 계정 도메인에 대한 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
open class AccountServiceImpl {
    private val accountRepository: AccountRepository? = null
    private val redisTemplate: RedisTemplate<String, String>? = null
    private val uploadService: UploadServiceImpl? = null

    /**
     * @Method : findAccountByAccountEmail
     * @Description : 이메일로 계정 조회
     * @Parameter : [accountEmail]
     * @Return : Account
     */
    fun findAccountByAccountEmail(accountEmail: String?): Account? {
        return accountRepository!!.findAccountByAccountEmail(accountEmail).orElse(null)
    }

    /**
     * @Method : findAccountByAccessToken
     * @Description : 액세스 토큰으로 계정 조회
     * @Parameter : [accessToken]
     * @Return : Account
     */
    @Cacheable("account")
    open fun findAccountByAccessToken(accessToken: String?): Account? {
        return accountRepository!!.findAccountByAccessToken(accessToken).orElseThrow(
            Supplier<NotFoundException> { NotFoundException() })
    }

    /**
     * @Method : findAccountByAccountId
     * @Description : accountId로 계정 조회
     * @Parameter : [accountId]
     * @Return : Account
     */
    fun findAccountByAccountId(accountId: Long): Account {
        return accountRepository!!.findById(accountId)
            .orElseThrow(Supplier<NotFoundException> { NotFoundException() })!!
    }

    /**
     * @Method : insertAccount
     * @Description : 회원 가입
     * @Parameter : [newAccount]
     * @Return : MyAccountResponse
     */
    fun insertAccount(newAccount: Account?): MyAccountResponse {
        val saveAccount = accountRepository!!.save(newAccount)
        return MyAccountResponse.Companion.toAccountResponse(saveAccount)
    }

    /**
     * @Method : updateAccount
     * @Description : 계정 정보 수정
     * @Parameter : [accessToken, accountId, accountUpdateRequest, multipartFile]
     * @Return : MyAccountResponse
     */
    @Throws(IOException::class)
    fun updateAccount(
        accessToken: String?, accountId: Long,
        accountUpdateRequest: AccountUpdateRequest,
        multipartFile: MultipartFile
    ): MyAccountResponse {
        val optionalUpdateAccount = findAccountByAccessToken(accessToken)
        if (optionalUpdateAccount?.accountId != accountId) {
            throw UnAuthorizedAccessException()
        }
        val updatedAccount = accountUpdateRequest.toAccount(optionalUpdateAccount)
        if (!multipartFile.isEmpty) {
            val uploadImg = uploadService!!.uploadImg(multipartFile)
            updatedAccount?.profileImageUrl = uploadImg
        }
        accountRepository!!.save(updatedAccount)
        return MyAccountResponse.Companion.toAccountResponse(updatedAccount)
    }

    /**
     * @Method : deleteAccount
     * @Description : 계정 삭제
     * @Parameter : [accessToken, accountId]
     * @Return : null
     */
    fun deleteAccount(accessToken: String?, accountId: Long) {
        val accountByAccessToken = findAccountByAccessToken(accessToken)
        if (accountByAccessToken?.accountId != accountId) {
            throw UnAuthorizedAccessException()
        }
        val key = "search::$accountId"
        redisTemplate?.delete(key)
        accountRepository!!.deleteById(accountId)
    }
}

