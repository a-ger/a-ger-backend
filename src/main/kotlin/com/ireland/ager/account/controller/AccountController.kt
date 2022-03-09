package com.ireland.ager.account.controller

import com.ireland.ager.account.dto.request.AccountUpdateRequest
import com.ireland.ager.account.dto.response.MyAccountResponse
import com.ireland.ager.account.dto.response.OtherAccountResponse
import com.ireland.ager.account.service.AccountServiceImpl
import com.ireland.ager.account.service.AuthServiceImpl
import com.ireland.ager.main.common.CommonResult
import com.ireland.ager.main.common.SingleResult
import com.ireland.ager.main.common.service.ResponseService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

/**
 * @Class : AccountController
 * @Description : 계정 도메인에 대한 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
@CrossOrigin(value = ["*"], maxAge = 6000)
class AccountController {
    private val accountService: AccountServiceImpl? = null
    private val authService: AuthServiceImpl? = null
    private val responseService: ResponseService? = null

    /**
     * @Method : loginUrl
     * @Description : 로그인 링크 반환
     * @Parameter : []
     * @Return : ResponseEntity<SingleResult></SingleResult><String>>
    </String> */
    @ApiOperation(value = "로그인 링크 생성")
    @GetMapping("/login-url")
    fun loginUrl(): ResponseEntity<SingleResult<String?>?> {
        return ResponseEntity(responseService!!.getSingleResult(authService?.kakaoLoginUrl), HttpStatus.OK)
    }

    /**
     * @Method : getTokenAndJoinOrLogin
     * @Description : 회원가입 또는 로그인
     * @Parameter : [code]
     * @Return : ResponseEntity<SingleResult></SingleResult><MyAccountResponse>>
    </MyAccountResponse> */
    @ApiOperation(value = "카카오 웹 로그인")
    @GetMapping("/login-web")
    fun getTokenAndWebJoinOrLogin(
        @ApiParam(value = "로그인 코드", required = true) @RequestParam("code") code: String?
    ): ResponseEntity<SingleResult<MyAccountResponse?>?> {
        return ResponseEntity(
            responseService!!.getSingleResult(
                authService!!.getKakaoWebLogin(code)
            ), HttpStatus.CREATED
        )
    }

    /**
     * @Method : getTokenAndJoinOrLogin
     * @Description : 회원가입 또는 로그인
     * @Parameter : [code]
     * @Return : ResponseEntity<SingleResult></SingleResult><MyAccountResponse>>
    </MyAccountResponse> */
    @GetMapping("/login")
    @ApiOperation(value = "카카오 로그인")
    fun getTokenAndJoinOrLogin(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<SingleResult<MyAccountResponse?>?> {
        return ResponseEntity(
            responseService!!.getSingleResult(
                authService!!.getKakaoLogin(accessToken)
            ), HttpStatus.CREATED
        )
    }

    /**
     * @Method : logout
     * @Description : 로그아웃
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<CommonResult>
    </CommonResult> */
    @ApiOperation(value = "카카오 로그아웃")
    @GetMapping("/logout")
    fun logout(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String
    ): ResponseEntity<CommonResult?> {
        authService!!.getKakaoLogout(accessToken)
        return ResponseEntity(responseService?.successResult, HttpStatus.OK)
    }
    /**
     * @Method : updateAccessToken
     * @Description : 액세스 토큰 업데이트
     * @Parameter : [accountId]
     * @Return : ResponseEntity<SingleResult></SingleResult><String>>
    </String> */
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
     * @Return : ResponseEntity<SingleResult></SingleResult><MyAccountResponse>>
    </MyAccountResponse> */
    @ApiOperation(value = "내 계정 정보 반환")
    @GetMapping
    fun getMyAccount(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String
    ): ResponseEntity<SingleResult<MyAccountResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        val myAccountResponse: MyAccountResponse = MyAccountResponse.Companion.toAccountResponse(
            accountService!!.findAccountByAccessToken(
                splitToken[1]
            )
        )
        return ResponseEntity(
            responseService!!.getSingleResult(myAccountResponse), HttpStatus.OK
        )
    }

    /**
     * @Method : getOtherAccount
     * @Description : 다른 사람 계정 정보 반환
     * @Parameter : [accessToken, accountId]
     * @Return : ResponseEntity<SingleResult></SingleResult><OtherAccountResponse>>
    </OtherAccountResponse> */
    @ApiOperation(value = "다른 사람 계정 정보 반환")
    @GetMapping("/{accountId}")
    fun getOtherAccount(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "accountId", required = true) @PathVariable accountId: Long
    ): ResponseEntity<SingleResult<OtherAccountResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        val otherAccountByAccountId: OtherAccountResponse = OtherAccountResponse.Companion.toOtherAccountResponse(
            accountService!!.findAccountByAccountId(accountId)
        )
        return ResponseEntity(
            responseService!!.getSingleResult(otherAccountByAccountId), HttpStatus.OK
        )
    }

    /**
     * @Method : updateAccount
     * @Description : 계정 정보 수정
     * @Parameter : [accessToken, accountId, accountUpdateRequest, multipartFile]
     * @Return : ResponseEntity<SingleResult></SingleResult><MyAccountResponse>>
    </MyAccountResponse> */
    @ApiOperation(value = "내 계정 정보 수정")
    @PatchMapping("/{accountId}")
    @Throws(IOException::class)
    fun updateAccount(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "accountId", required = true) @PathVariable accountId: Long,
        @ApiParam(
            value = "수정할 정보",
            required = true
        ) @RequestPart(value = "update") accountUpdateRequest: AccountUpdateRequest,
        @ApiParam(value = "수정할 사진", required = true) @RequestPart(value = "file") multipartFile: MultipartFile
    ): ResponseEntity<SingleResult<MyAccountResponse?>?> {
        val spitToken = accessToken.split(" ").toTypedArray()
        val myAccountResponse =
            accountService!!.updateAccount(spitToken[1], accountId, accountUpdateRequest, multipartFile)
        return ResponseEntity(responseService!!.getSingleResult(myAccountResponse), HttpStatus.OK)
    }

    /**
     * @Method : deleteAccount
     * @Description : 계정 삭제
     * @Parameter : [accessToken, accountId]
     * @Return : ResponseEntity<CommonResult>
    </CommonResult> */
    @ApiOperation(value = "내 계정 삭제")
    @DeleteMapping("/{accountId}")
    fun deleteAccount(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "accountId", required = true) @PathVariable accountId: Long
    ): ResponseEntity<CommonResult?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        accountService!!.deleteAccount(splitToken[1], accountId)
        return ResponseEntity(responseService?.successResult, HttpStatus.OK)
    }
}