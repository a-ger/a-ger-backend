package com.ireland.ager.account.service;

import com.ireland.ager.account.dto.response.MyAccountResponse;
import com.ireland.ager.account.dto.response.KakaoResponse;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.exception.ExpiredAccessTokenException;
import com.ireland.ager.account.exception.NotFoundTokenException;
import com.ireland.ager.account.exception.UnAuthorizedTokenException;
import com.ireland.ager.account.repository.AccountRepository;
import com.ireland.ager.main.exception.IntenalServerErrorException;
import com.ireland.ager.main.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

/**
 * @Class : AuthServiceImpl
 * @Description : 계정 도메인에 대한 인증 서비스
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final AccountRepository accountRepository;
    private final AccountServiceImpl accountService;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoRestApiKey;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoRestSecretKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;

    private final String KAKAO_URL = "https://kauth.kakao.com";
    private final String TOKEN_TYPE = "Bearer";
    private final String EMAIL_SUFFIX = "@gmail.com";

    /**
     * @Method : getKakaoLoginUrl
     * @Description : 로그인 링크 반환
     * @Parameter : []
     * @Return : String
     **/
    public String getKakaoLoginUrl() {
        return new StringBuilder()
                .append(KAKAO_URL).append("/oauth/authorize?client_id=").append(kakaoRestApiKey)
                .append("&redirect_uri=").append(kakaoRedirectUrl)
                .append("&response_type=code")
                .toString();
    }
    public MyAccountResponse getKakaoWebLogin(String code) {
        HashMap<String, String> kakaoTokens = getKakaoTokens(code);
        KakaoResponse kakaoResponse = getKakaoUserInfo(kakaoTokens.get("access_token"));

        String accountEmail = kakaoResponse.getKakao_account().getEmail();
        //TODO 나중에 카카오 인증으로 이메일 필수 동의할 수 있게 하자
        if (accountEmail == null || accountEmail == "") {
            accountEmail = String.valueOf(kakaoResponse.getId()) + EMAIL_SUFFIX;
        }
        Account accountForCheck = accountService.findAccountByAccountEmail(accountEmail);
        if (accountForCheck != null) {
            // 존재한다면 Token 값을 갱신하고 반환한다.
            return updateTokenWithAccount(accountForCheck.getAccountId(), kakaoTokens.get("access_token"));
        } else {
            // 존재하지 않는다면 회원 가입 시키고 반환한다.
            return accountService.insertAccount(
                    kakaoResponse.toAccount(kakaoTokens.get("access_token")));
        }
    }
    /**
     * @Method : getKakaoLogin
     * @Description : 카카오 로그인
     * @Parameter : [code]
     * @Return : MyAccountResponse
     **/
    public MyAccountResponse getKakaoLogin(String accessToken) {
        //HashMap<String, String> kakaoTokens = getKakaoTokens(code);
        KakaoResponse kakaoResponse = getKakaoUserInfo(accessToken);

        String accountEmail = kakaoResponse.getKakao_account().getEmail();
        //TODO 나중에 카카오 인증으로 이메일 필수 동의할 수 있게 하자
        if (accountEmail == null || accountEmail == "") {
            accountEmail = String.valueOf(kakaoResponse.getId()) + EMAIL_SUFFIX;
        }
        Account accountForCheck = accountService.findAccountByAccountEmail(accountEmail);
        if (accountForCheck != null) {
            // 존재한다면 Token 값을 갱신하고 반환한다.
            return updateTokenWithAccount(accountForCheck.getAccountId(), accessToken);
        } else {
            // 존재하지 않는다면 회원 가입 시키고 반환한다.
            return accountService.insertAccount(
                    kakaoResponse.toAccount(accessToken));
        }
    }

    /**
     * @Method : getKakaoLogout
     * @Description : 카카오 로그아웃
     * @Parameter : [accessToken]
     * @Return : null
     **/
    public void getKakaoLogout(String accessToken) {
        isValidToken(accessToken);
        Account accountByAccessToken = accountService.findAccountByAccessToken(accessToken.split(" ")[1]);
        String logoutHost = "https://kapi.kakao.com/v1/user/logout";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", TOKEN_TYPE + " " + accountByAccessToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> logoutKakaoReq = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(logoutHost, HttpMethod.POST, logoutKakaoReq, HashMap.class);

        Optional<Account> optionalLogoutAccount = accountRepository.findById(accountByAccessToken.getAccountId());
        optionalLogoutAccount.ifPresent(accountRepository::save);
    }

    /**
     * @Method : getKakaoTokens
     * @Description : 카카오 토큰 조회
     * @Parameter : [code]
     * @Return : HashMap<String, String>
     **/
    public HashMap<String, String> getKakaoTokens(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> httpBody = new LinkedMultiValueMap<>();
        httpBody.add("grant_type", "authorization_code");
        httpBody.add("client_id", kakaoRestApiKey);
        httpBody.add("redirect_uri", kakaoRedirectUrl);
        httpBody.add("code", code);
        httpBody.add("client_secret", kakaoRestSecretKey);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenReq = new HttpEntity<>(httpBody, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> tokenResEntity = restTemplate.exchange(KAKAO_URL + "/oauth/token", HttpMethod.POST, kakaoTokenReq, HashMap.class);

        return tokenResEntity.getBody();
    }

    /**
     * @Method : getKakaoUserInfo
     * @Description : 카카오 유저 정보 조회
     * @Parameter : [accessToken]
     * @Return : KakaoResponse
     **/
    public KakaoResponse getKakaoUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", TOKEN_TYPE + " " + accessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoReq = new HttpEntity<>(httpHeaders);
        ResponseEntity<KakaoResponse> userInfo = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, kakaoUserInfoReq, KakaoResponse.class);

        return userInfo.getBody();
    }

    /**
     * @Method : updateTokenWithAccount
     * @Description : 카카오 토큰 갱신
     * @Parameter : [accountId, accessToken, refreshToken]
     * @Return : MyAccountResponse
     **/
    public MyAccountResponse updateTokenWithAccount(Long accountId, String accessToken) {
        Optional<Account> optionalExistAccount = accountRepository.findById(accountId);
        Account existAccount = optionalExistAccount.map(account -> {
            account.setAccessToken(accessToken);
            return account;
        }).orElseThrow(NotFoundException::new);

        accountRepository.save(existAccount);
        return MyAccountResponse.toAccountResponse(existAccount);
    }

    /**
     * @Method : updateTokenWithAccount
     * @Description : 카카오 액세스 토큰 갱신
     * @Parameter : [accountId]
     * @Return : String
     **/
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
     **/
    public void isValidToken(String accessToken) {
        if (accessToken.isEmpty()) throw new NotFoundTokenException();
        String vaildCheckHost = "https://kapi.kakao.com/v1/user/access_token_info";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", accessToken);
        HttpEntity<MultiValueMap<String, String>> kakaoValidTokenReq = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<HashMap> isValidEntity = restTemplate.exchange(vaildCheckHost, HttpMethod.GET, kakaoValidTokenReq, HashMap.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401 || e.getStatusCode().value() == 400)
                throw new UnAuthorizedTokenException();
            else
                throw new IntenalServerErrorException();
        }
    }
}