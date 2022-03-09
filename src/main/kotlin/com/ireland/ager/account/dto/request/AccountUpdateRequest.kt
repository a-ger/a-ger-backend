package com.ireland.ager.account.dto.request

import com.ireland.ager.account.entity.Account

/**
 * @Class : AccountUpdateRequest
 * @Description : 계정 도메인에 대한 Request DTO
 */
data class AccountUpdateRequest(var profileNickname: String) {
    /**
     * @Method : toAccount
     * @Description : 계정 정보 수정 데이터 객체화
     * @Parameter : [updateAccount]
     * @Return : Account
     */
    fun toAccount(updateAccount: Account?): Account? {
        updateAccount?.profileNickname=profileNickname
        return updateAccount
    }
}