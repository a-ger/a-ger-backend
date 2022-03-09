package com.ireland.ager.account.controller

import com.ireland.ager.account.service.AccountInfoServiceImpl
import com.ireland.ager.board.dto.response.BoardSummaryResponse
import com.ireland.ager.main.common.SliceResult
import com.ireland.ager.main.common.service.ResponseService
import com.ireland.ager.product.dto.response.ProductThumbResponse
import com.ireland.ager.review.dto.response.ReviewResponse
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @Class : InfoController
 * @Description : 계정 정보 도메인에 대한 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account/{accountId}")
@CrossOrigin(value = ["*"], maxAge = 6000)
class InfoController {
    private val responseService: ResponseService? = null
    private val accountInfoService: AccountInfoServiceImpl? = null

    /**
     * @Method : findSellsByAccountId
     * @Description : 판매 내역 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult></SliceResult><ProductThumbResponse>>
    </ProductThumbResponse> */
    @ApiOperation(value = "내 판매 내역 조회")
    @GetMapping("/sells")
    fun findSellsByAccountId(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "accountId", required = true) @PathVariable accountId: Long?,
        @ApiParam(value = "pageable", required = false) pageable: Pageable?
    ): ResponseEntity<SliceResult<ProductThumbResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        return ResponseEntity(
            responseService!!.getSliceResult(
                accountInfoService!!.findSellsByAccountId(accountId, pageable)!!
            ), HttpStatus.CREATED
        )
    }

    /**
     * @Method : findBuysByAccountId
     * @Description : 구매 내역 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult></SliceResult><ProductThumbResponse>>
    </ProductThumbResponse> */
    @ApiOperation(value = "내 구매 내역 조회")
    @GetMapping("/buys")
    fun findBuysByAccountId(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "accountId", required = true) @PathVariable accountId: Long,
        @ApiParam(value = "pageable", required = false) pageable: Pageable?
    ): ResponseEntity<SliceResult<ProductThumbResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        return ResponseEntity(
            responseService!!.getSliceResult(
                accountInfoService!!.findBuysByACcountId(splitToken[1], accountId, pageable)
            ), HttpStatus.CREATED
        )
    }

    /**
     * @Method : findReviewsByAccountId
     * @Description : 받은 리뷰 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult></SliceResult><ReviewResponse>>
    </ReviewResponse> */
    @ApiOperation(value = "내가 받은 리뷰 조회")
    @GetMapping("/reviews")
    fun findReviewsByAccountId(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "accountId", required = true) @PathVariable accountId: Long?,
        @ApiParam(value = "pageable", required = false) pageable: Pageable?
    ): ResponseEntity<SliceResult<ReviewResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        return ResponseEntity(
            responseService!!.getSliceResult(
                accountInfoService!!.findReviewsByAccountId(accountId, pageable)
            ), HttpStatus.CREATED
        )
    }

    /**
     * @Method : findBoardsByAccountId
     * @Description : 게시한 게시글 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult></SliceResult><BoardSummaryResponse>>
    </BoardSummaryResponse> */
    @ApiOperation(value = "내가 게시한 게시글 조회")
    @GetMapping("/boards")
    fun findBoardsByAccountId(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "accountId", required = true) @PathVariable accountId: Long?,
        @ApiParam(value = "pageable", required = false) pageable: Pageable?
    ): ResponseEntity<SliceResult<BoardSummaryResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        return ResponseEntity(
            responseService!!.getSliceResult(
                accountInfoService!!.findBoardsByAccountId(accountId, pageable)
            ), HttpStatus.CREATED
        )
    }
}