package com.ireland.ager.account.service

import com.ireland.ager.account.dto.response.KakaoResponse
import com.ireland.ager.account.dto.response.MyAccountResponse
import com.ireland.ager.account.entity.Account
import com.ireland.ager.account.exception.NotFoundTokenException
import com.ireland.ager.account.exception.UnAuthorizedTokenException
import com.ireland.ager.account.repository.AccountRepository
import com.ireland.ager.main.exception.IntenalServerErrorException
import com.ireland.ager.main.exception.NotFoundException
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.function.Supplier

/**
 * @Class : AuthServiceImpl
 * @Description : 계정 도메인에 대한 인증 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
open class AuthServiceImpl {
    private val accountRepository: AccountRepository? = null
    private val accountService: AccountServiceImpl? = null

    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private val kakaoRestApiKey: String? = null

    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}")
    private val kakaoRestSecretKey: String? = null

    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private val kakaoRedirectUrl: String? = null

    @Value("\${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private val kakaoUserInfoUrl: String? = null
    private val KAKAO_URL = "https://kauth.kakao.com"
    private val TOKEN_TYPE = "Bearer"
    private val EMAIL_SUFFIX = "@gmail.com"

    /**
     * @Method : getKakaoLoginUrl
     * @Description : 로그인 링크 반환
     * @Parameter : []
     * @Return : String
     */
    val kakaoLoginUrl: String
        get() = StringBuilder()
            .append(KAKAO_URL).append("/oauth/authorize?client_id=").append(kakaoRestApiKey)
            .append("&redirect_uri=").append(kakaoRedirectUrl)
            .append("&response_type=code")
            .toString()

    fun getKakaoWebLogin(code: String?): MyAccountResponse? {
        val kakaoTokens = getKakaoTokens(code)
        val kakaoResponse = getKakaoUserInfo(kakaoTokens?.get("access_token") as String?)
        var accountEmail = kakaoResponse.kakao_account.email
        //TODO 나중에 카카오 인증으로 이메일 필수 동의할 수 있게 하자
        if (accountEmail == null || accountEmail === "") {
            accountEmail = kakaoResponse.id.toString() + EMAIL_SUFFIX
        }
        val accountForCheck = accountService!!.findAccountByAccountEmail(accountEmail)
        return if (accountForCheck != null) {
            // 존재한다면 Token 값을 갱신하고 반환한다.
            updateTokenWithAccount(accountForCheck.accountId, kakaoTokens?.get("access_token") as String?)
        } else {
            // 존재하지 않는다면 회원 가입 시키고 반환한다.
            accountService.insertAccount(
                kakaoResponse.toAccount(kakaoTokens?.get("access_token") as String?)
            )
        }
    }

    /**
     * @Method : getKakaoLogin
     * @Description : 카카오 로그인
     * @Parameter : [code]
     * @Return : MyAccountResponse
     */
    fun getKakaoLogin(accessToken: String?): MyAccountResponse? {
        //HashMap<String, String> kakaoTokens = getKakaoTokens(code);
        val kakaoResponse = getKakaoUserInfo(accessToken)
        var accountEmail = kakaoResponse.kakao_account.email
        //TODO 나중에 카카오 인증으로 이메일 필수 동의할 수 있게 하자
        if (accountEmail == null || accountEmail === "") {
            accountEmail = kakaoResponse.id.toString() + EMAIL_SUFFIX
        }
        val accountForCheck = accountService!!.findAccountByAccountEmail(accountEmail)
        return if (accountForCheck != null) {
            // 존재한다면 Token 값을 갱신하고 반환한다.
            updateTokenWithAccount(accountForCheck.accountId, accessToken)
        } else {
            // 존재하지 않는다면 회원 가입 시키고 반환한다.
            accountService.insertAccount(
                kakaoResponse.toAccount(accessToken)
            )
        }
    }

    /**
     * @Method : getKakaoLogout
     * @Description : 카카오 로그아웃
     * @Parameter : [accessToken]
     * @Return : null
     */
    fun getKakaoLogout(accessToken: String) {
        isValidToken(accessToken)
        val accountByAccessToken = accountService!!.findAccountByAccessToken(accessToken.split(" ").toTypedArray()[1])
        val logoutHost = "https://kapi.kakao.com/v1/user/logout"
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        httpHeaders.add("Authorization", TOKEN_TYPE + " " + accountByAccessToken?.accessToken)
        val logoutKakaoReq = HttpEntity<MultiValueMap<String, String>>(httpHeaders)
        val restTemplate = RestTemplate()
        restTemplate.exchange(logoutHost, HttpMethod.POST, logoutKakaoReq, HashMap::class.java)
        val optionalLogoutAccount = accountRepository!!.findById(accountByAccessToken?.accountId)
        optionalLogoutAccount.ifPresent { entity: Account -> accountRepository.save(entity) }
    }

    /**
     * @Method : getKakaoTokens
     * @Description : 카카오 토큰 조회
     * @Parameter : [code]
     * @Return : HashMap<String></String>, String>
     */
    fun getKakaoTokens(code: String?): HashMap<*, *>? {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        val httpBody: MultiValueMap<String, String?> = LinkedMultiValueMap()
        httpBody.add("grant_type", "authorization_code")
        httpBody.add("client_id", kakaoRestApiKey)
        httpBody.add("redirect_uri", kakaoRedirectUrl)
        httpBody.add("code", code)
        httpBody.add("client_secret", kakaoRestSecretKey)
        val kakaoTokenReq = HttpEntity(httpBody, httpHeaders)
        val restTemplate = RestTemplate()
        val tokenResEntity =
            restTemplate.exchange("$KAKAO_URL/oauth/token", HttpMethod.POST, kakaoTokenReq, HashMap::class.java)
        return tokenResEntity.body
    }

    /**
     * @Method : getKakaoUserInfo
     * @Description : 카카오 유저 정보 조회
     * @Parameter : [accessToken]
     * @Return : KakaoResponse
     */
    fun getKakaoUserInfo(accessToken: String?): KakaoResponse {
        val restTemplate = RestTemplate()
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        httpHeaders.add("Authorization", "$TOKEN_TYPE $accessToken")
        val kakaoUserInfoReq = HttpEntity<MultiValueMap<String, String>>(httpHeaders)
        val userInfo =
            restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, kakaoUserInfoReq, KakaoResponse::class.java)
        return userInfo.body
    }

    /**
     * @Method : updateTokenWithAccount
     * @Description : 카카오 토큰 갱신
     * @Parameter : [accountId, accessToken, refreshToken]
     * @Return : MyAccountResponse
     */
    fun updateTokenWithAccount(accountId: Long?, accessToken: String?): MyAccountResponse {
        val optionalExistAccount = accountRepository!!.findById(accountId)
        val existAccount = optionalExistAccount.map { account: Account? ->
            account?.accessToken=accessToken
            account
        }
            .orElseThrow(Supplier<NotFoundException> { NotFoundException() })
        accountRepository.save(existAccount)
        return MyAccountResponse.Companion.toAccountResponse(existAccount)
    }
    /**
     * @Method : updateTokenWithAccount
     * @Description : 카카오 액세스 토큰 갱신
     * @Parameter : [accountId]
     * @Return : String
     */
    /*
    public String updateAccessToken(Long accountId) {
        Optional<Account> optionalAccountForUpdate = accountRepository.findById(accountId);
        Account accountForUpdate = optionalAccountForUpdate.orElseThrow(NotFoundException::new);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> httpBody = new LinkedMultiValueMap<>();
        httpBody.add("grant_type", "refresh_token");
        httpBody.add("client_id", kakaoRestApiKey);
        httpBody.add("refresh_token", accountForUpdate.getRefreshToken());
        httpBody.add("client_secret", kakaoRestSecretKey);

        HttpEntity<MultiValueMap<String, String>> kakaoUpdateTokenReq = new HttpEntity<>(httpBody, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> tokenResEntity = restTemplate.exchange(KAKAO_URL + "/oauth/token", HttpMethod.POST, kakaoUpdateTokenReq, HashMap.class);

        String newToken = (String) tokenResEntity.getBody().get("access_token");
        accountForUpdate.setAccessToken(newToken);
        accountRepository.save(accountForUpdate);
        if (newToken == null)
            throw new ExpiredAccessTokenException();

        return newToken;
    }
     */
    /**
     * @Method : isValidToken
     * @Description : 토큰 유효성 검사
     * @Parameter : [accessToken]
     * @Return : null
     */
    fun isValidToken(accessToken: String) {
        if (accessToken.isEmpty()) throw NotFoundTokenException()
        val vaildCheckHost = "https://kapi.kakao.com/v1/user/access_token_info"
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        httpHeaders.add("Authorization", accessToken)
        val kakaoValidTokenReq = HttpEntity<MultiValueMap<String, String>>(httpHeaders)
        val restTemplate = RestTemplate()
        try {
            val isValidEntity =
                restTemplate.exchange(vaildCheckHost, HttpMethod.GET, kakaoValidTokenReq, HashMap::class.java)
        } catch (e: HttpClientErrorException) {
            if (e.statusCode.value() == 401 || e.statusCode.value() == 400) throw UnAuthorizedTokenException() else throw IntenalServerErrorException()
        }
    }
}