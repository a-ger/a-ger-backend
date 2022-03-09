package com.ireland.ager.account.repository

import com.ireland.ager.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @Class : AccountRepository
 * @Description : 계정 도메인에 대한 레포지토리
 */
@Repository
interface AccountRepository : JpaRepository<Account?, Long?> {
    fun findAccountByAccessToken(accessToken: String?): Optional<Account?>
    fun findAccountByAccountEmail(accountEmail: String?): Optional<Account?>
}