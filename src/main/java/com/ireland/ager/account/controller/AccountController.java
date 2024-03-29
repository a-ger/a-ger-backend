package com.ireland.ager.account.controller;

import com.ireland.ager.account.dto.request.AccountUpdateRequest;
import com.ireland.ager.account.dto.response.MyAccountResponse;
import com.ireland.ager.account.dto.response.OtherAccountResponse;
import com.ireland.ager.account.service.AccountServiceImpl;
import com.ireland.ager.account.service.AuthServiceImpl;
import com.ireland.ager.main.common.CommonResult;
import com.ireland.ager.main.common.SingleResult;
import com.ireland.ager.main.common.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Class : AccountController
 * @Description : 계정 도메인에 대한 컨트롤러
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
@CrossOrigin(value = {"*"}, maxAge = 6000)
public class AccountController {

    private final AccountServiceImpl accountService;

    private final AuthServiceImpl authService;

    private final ResponseService responseService;
  
    /**
     * @Method : loginUrl
     * @Description : 로그인 링크 반환
     * @Parameter : []
     * @Return : ResponseEntity<SingleResult<String>>
     **/
    @ApiOperation(value="로그인 링크 생성")
    @GetMapping("/login-url")
    public ResponseEntity<SingleResult<String>> loginUrl() {
        return new ResponseEntity<>(responseService.getSingleResult(authService.getKakaoLoginUrl()), HttpStatus.OK);
    }

    /**
     * @Method : getTokenAndJoinOrLogin
     * @Description : 회원가입 또는 로그인
     * @Parameter : [code]
     * @Return : ResponseEntity<SingleResult<MyAccountResponse>>
     **/
    @ApiOperation(value="카카오 웹 로그인")
    @GetMapping("/login-web")
    public ResponseEntity<SingleResult<MyAccountResponse>> getTokenAndWebJoinOrLogin(
            @ApiParam(value = "로그인 코드", required = true)
            @RequestParam("code") String code) {
        return new ResponseEntity<>(responseService.getSingleResult(authService.getKakaoWebLogin(code)), HttpStatus.CREATED);
    }

    /**
     * @Method : getTokenAndJoinOrLogin
     * @Description : 회원가입 또는 로그인
     * @Parameter : [code]
     * @Return : ResponseEntity<SingleResult<MyAccountResponse>>
     **/
    @GetMapping("/login")
    @ApiOperation(value="카카오 로그인")
    public ResponseEntity<SingleResult<MyAccountResponse>> getTokenAndJoinOrLogin(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken) {
        return new ResponseEntity<>(responseService.getSingleResult(authService.getKakaoLogin(accessToken)), HttpStatus.CREATED);
    }
  
    /**
     * @Method : logout
     * @Description : 로그아웃
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<CommonResult>
     **/
    @ApiOperation(value="카카오 로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<CommonResult> logout(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken) {
        authService.getKakaoLogout(accessToken);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }

    /**
     * @Method : updateAccessToken
     * @Description : 액세스 토큰 업데이트
     * @Parameter : [accountId]
     * @Return : ResponseEntity<SingleResult<String>>
     **/
    /*
    @GetMapping("/token/{accountId}")
    public ResponseEntity<SingleResult<String>> updateAccessToken(@PathVariable Long accountId) {
        String newToken = authService.updateAccessToken(accountId);
        return new ResponseEntity<>(responseService.getSingleResult(newToken), HttpStatus.OK);
    }
     */
    /**
     * @Method : getMyAccount
     * @Description : 내 계정 정보 반환
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<SingleResult<MyAccountResponse>>
     **/
    @ApiOperation(value="내 계정 정보 반환")
    @GetMapping
    public ResponseEntity<SingleResult<MyAccountResponse>> getMyAccount(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken) {
        String[] splitToken = accessToken.split(" ");
        System.out.println("splitToken = " + splitToken[1]);
        MyAccountResponse myAccountResponse = MyAccountResponse.toAccountResponse(accountService.findAccountByAccessToken(splitToken[1]));
        return new ResponseEntity<>(
                responseService.getSingleResult(myAccountResponse), HttpStatus.OK);
    }

    /**
     * @Method : getOtherAccount
     * @Description : 다른 사람 계정 정보 반환
     * @Parameter : [accessToken, accountId]
     * @Return : ResponseEntity<SingleResult<OtherAccountResponse>>
     **/
    @ApiOperation(value="다른 사람 계정 정보 반환")
    @GetMapping("/{accountId}")
    public ResponseEntity<SingleResult<OtherAccountResponse>> getOtherAccount(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "accountId", required = true)
            @PathVariable Long accountId) {
        String[] splitToken = accessToken.split(" ");
        OtherAccountResponse otherAccountByAccountId = OtherAccountResponse.toOtherAccountResponse(accountService.findAccountByAccountId(accountId));
        return new ResponseEntity<>(
                responseService.getSingleResult(otherAccountByAccountId), HttpStatus.OK);
    }

    /**
     * @Method : updateAccount
     * @Description : 계정 정보 수정
     * @Parameter : [accessToken, accountId, accountUpdateRequest, multipartFile]
     * @Return : ResponseEntity<SingleResult<MyAccountResponse>>
     **/
    @ApiOperation(value="내 계정 정보 수정")
    @PatchMapping("/{accountId}")
    public ResponseEntity<SingleResult<MyAccountResponse>> updateAccount(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "accountId", required = true)
            @PathVariable Long accountId,
            @ApiParam(value = "수정할 정보", required = true)
            @RequestPart(value = "update") AccountUpdateRequest accountUpdateRequest,
            @ApiParam(value = "수정할 사진", required = true)
            @RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String[] spitToken = accessToken.split(" ");
        MyAccountResponse myAccountResponse = accountService.updateAccount(spitToken[1], accountId, accountUpdateRequest, multipartFile);
        return new ResponseEntity<>(responseService.getSingleResult(myAccountResponse), HttpStatus.OK);
    }

    /**
     * @Method : deleteAccount
     * @Description : 계정 삭제
     * @Parameter : [accessToken, accountId]
     * @Return : ResponseEntity<CommonResult>
     **/
    @ApiOperation(value="내 계정 삭제")
    @DeleteMapping("/{accountId}")
    public ResponseEntity<CommonResult> deleteAccount(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "accountId", required = true)
            @PathVariable Long accountId) {
        String[] splitToken = accessToken.split(" ");
        accountService.deleteAccount(splitToken[1], accountId);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
}