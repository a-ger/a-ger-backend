package com.ireland.ager.account.dto.response

import com.ireland.ager.account.entity.Account
import java.time.LocalDateTime

/**
 * @Class : MyAccountResponse
 * @Description : 계정 도메인에 대한 Response DTO
 */
data class MyAccountResponse internal constructor(
    var accountId: Long?,
    var accountEmail: String?,
    var profileNickname: String?,
    var userName: String?,
    var profileImageUrl: String?,
    var accessToken: String?,
    var avgStar: Double?,
    var createdAt: LocalDateTime?
) {

    class MyAccountResponseBuilder internal constructor() {
        private var accountId: Long? = null
        private var accountEmail: String? = null
        private var profileNickname: String? = null
        private var userName: String? = null
        private var profileImageUrl: String? = null
        private var accessToken: String? = null
        private var avgStar: Double? = null
        private var createdAt: LocalDateTime? = null
        fun accountId(accountId: Long?): MyAccountResponseBuilder {
            this.accountId = accountId
            return this
        }

        fun accountEmail(accountEmail: String?): MyAccountResponseBuilder {
            this.accountEmail = accountEmail
            return this
        }

        fun profileNickname(profileNickname: String?): MyAccountResponseBuilder {
            this.profileNickname = profileNickname
            return this
        }

        fun userName(userName: String?): MyAccountResponseBuilder {
            this.userName = userName
            return this
        }

        fun profileImageUrl(profileImageUrl: String?): MyAccountResponseBuilder {
            this.profileImageUrl = profileImageUrl
            return this
        }

        fun accessToken(accessToken: String?): MyAccountResponseBuilder {
            this.accessToken = accessToken
            return this
        }

        fun avgStar(avgStar: Double?): MyAccountResponseBuilder {
            this.avgStar = avgStar
            return this
        }

        fun createdAt(createdAt: LocalDateTime?): MyAccountResponseBuilder {
            this.createdAt = createdAt
            return this
        }

        fun build(): MyAccountResponse {
            return MyAccountResponse(
                accountId,
                accountEmail,
                profileNickname,
                userName,
                profileImageUrl,
                accessToken,
                avgStar,
                createdAt
            )
        }
    }

    companion object {
        /**
         * @Method : toAccountResponse
         * @Description : 계정 정보 데이터 응답 객체화
         * @Parameter : [account]
         * @Return : MyAccountResponse
         */
        fun toAccountResponse(account: Account?): MyAccountResponse {
            return builder()
                .accessToken(account.getAccessToken())
                .profileNickname(account.getProfileNickname())
                .userName(account.getUserName())
                .accountEmail(account.getAccountEmail())
                .profileImageUrl(account.getProfileImageUrl())
                .accountId(account.getAccountId())
                .avgStar(account.getAvgStar())
                .createdAt(account.getCreatedAt())
                .build()
        }

        fun builder(): MyAccountResponseBuilder {
            return MyAccountResponseBuilder()
        }
    }
}