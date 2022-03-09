package com.ireland.ager.account.dto.response

import com.ireland.ager.account.entity.Account
import java.time.LocalDateTime

/**
 * @Class : OtherAccountResponse
 * @Description : 계정 도메인에 대한 Response DTO
 */
data class OtherAccountResponse internal constructor(
    var accountId: Long?,
    var accountEmail: String?,
    var profileNickname: String?,
    var profileImageUrl: String?,
    var avgStar: Double?,
    var createdAt: LocalDateTime?
) {
    class OtherAccountResponseBuilder internal constructor() {
        private var accountId: Long? = null
        private var accountEmail: String? = null
        private var profileNickname: String? = null
        private var profileImageUrl: String? = null
        private var avgStar: Double? = null
        private var createdAt: LocalDateTime? = null
        fun accountId(accountId: Long?): OtherAccountResponseBuilder {
            this.accountId = accountId
            return this
        }

        fun accountEmail(accountEmail: String?): OtherAccountResponseBuilder {
            this.accountEmail = accountEmail
            return this
        }

        fun profileNickname(profileNickname: String?): OtherAccountResponseBuilder {
            this.profileNickname = profileNickname
            return this
        }

        fun profileImageUrl(profileImageUrl: String?): OtherAccountResponseBuilder {
            this.profileImageUrl = profileImageUrl
            return this
        }

        fun avgStar(avgStar: Double?): OtherAccountResponseBuilder {
            this.avgStar = avgStar
            return this
        }

        fun createdAt(createdAt: LocalDateTime?): OtherAccountResponseBuilder {
            this.createdAt = createdAt
            return this
        }

        fun build(): OtherAccountResponse {
            return OtherAccountResponse(accountId, accountEmail, profileNickname, profileImageUrl, avgStar, createdAt)
        }

        override fun toString(): String {
            return "OtherAccountResponse.OtherAccountResponseBuilder(accountId=" + accountId + ", accountEmail=" + accountEmail + ", profileNickname=" + profileNickname + ", profileImageUrl=" + profileImageUrl + ", avgStar=" + avgStar + ", createdAt=" + createdAt + ")"
        }
    }

    companion object {
        /**
         * @Method : toOtherAccountResponse
         * @Description : 계정 정보 데이터 응답 객체화
         * @Parameter : [account]
         * @Return : OtherAccountResponse
         */
        fun toOtherAccountResponse(account: Account?): OtherAccountResponse {
            return builder()
                .profileNickname(account?.profileNickname)
                .accountEmail(account?.accountEmail)
                .profileImageUrl(account?.profileImageUrl)
                .accountId(account?.accountId)
                .createdAt(account?.createdAt)
                .avgStar(account?.avgStar)
                .build()
        }

        fun builder(): OtherAccountResponseBuilder {
            return OtherAccountResponseBuilder()
        }
    }
}