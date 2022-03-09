package com.ireland.ager.account.dto.response

import com.ireland.ager.account.entity.Account

/**
 * @Class : KakaoResponse
 * @Description : 카카오 도메인에 대한 Response DTO
 */
data class KakaoResponse(
    var id: Int,
    var connected_at: String,
    var properties: Properties,
    var kakao_account: KakaoAccount
) {
    inner class Properties(
        var nickname: String,
        var profile_image: String,
        var thumbnail_image: String
    )
    inner class KakaoAccount(
        var profile_needs_agreement: Boolean,
        var profile: Profile,
        var has_email: Boolean,
        var email_needs_agreement: Boolean,
        var is_email_valid: Boolean,
        var is_email_verified: Boolean,
        var email: String
    ) {
        inner class Profile(
            var nickname: String,
            var thumbnail_image_url: String,
            var profile_image_url: String
        )
    }

    /**
     * @Method : toAccount
     * @Description : 카카오 계정 정보 데이터 응답 객체화
     * @Parameter : [accessToken, refreshToken]
     * @Return : Account
     */
    fun toAccount(accessToken: String?): Account {
        val account = Account()
        if (kakao_account!!.email == null || kakao_account!!.email == "") account.accountEmail =
            id.toString() else account.accountEmail = kakao_account!!.email
        account.profileNickname = properties!!.nickname
        account.userName = kakao_account!!.profile!!.nickname
        account.profileImageUrl = kakao_account!!.profile!!.profile_image_url
        account.accessToken = accessToken
        return account
    }
}