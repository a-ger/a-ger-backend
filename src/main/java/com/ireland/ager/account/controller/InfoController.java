package com.ireland.ager.account.controller;


import com.ireland.ager.account.service.AccountInfoServiceImpl;
import com.ireland.ager.board.dto.response.BoardSummaryResponse;
import com.ireland.ager.main.common.SliceResult;
import com.ireland.ager.main.common.service.ResponseService;
import com.ireland.ager.product.dto.response.ProductThumbResponse;
import com.ireland.ager.review.dto.response.ReviewResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : InfoController
 * @Description : 계정 정보 도메인에 대한 컨트롤러
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account/{accountId}")
@CrossOrigin(value = {"*"}, maxAge = 6000)
public class InfoController {
    private final ResponseService responseService;
    private final AccountInfoServiceImpl accountInfoService;

    /**
     * @Method : findSellsByAccountId
     * @Description : 판매 내역 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult<ProductThumbResponse>>
     **/
    @ApiOperation(value="내 판매 내역 조회")
    @GetMapping("/sells")
    public ResponseEntity<SliceResult<ProductThumbResponse>> findSellsByAccountId(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "accountId", required = true)
            @PathVariable Long accountId,
            @ApiParam(value = "pageable", required = false)
            Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getSliceResult(accountInfoService.findSellsByAccountId(accountId, pageable)), HttpStatus.CREATED);
    }

    /**
     * @Method : findBuysByAccountId
     * @Description : 구매 내역 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult<ProductThumbResponse>>
     **/
    @ApiOperation(value="내 구매 내역 조회")
    @GetMapping("/buys")
    public ResponseEntity<SliceResult<ProductThumbResponse>> findBuysByAccountId(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "accountId", required = true)
            @PathVariable Long accountId,
            @ApiParam(value = "pageable", required = false)
            Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getSliceResult(accountInfoService.findBuysByACcountId(splitToken[1], accountId, pageable)), HttpStatus.CREATED);
    }

    /**
     * @Method : findReviewsByAccountId
     * @Description : 받은 리뷰 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult<ReviewResponse>>
     **/
    @ApiOperation(value="내가 받은 리뷰 조회")
    @GetMapping("/reviews")
    public ResponseEntity<SliceResult<ReviewResponse>> findReviewsByAccountId(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "accountId", required = true)
            @PathVariable Long accountId,
            @ApiParam(value = "pageable", required = false)
            Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getSliceResult(accountInfoService.findReviewsByAccountId(accountId, pageable)), HttpStatus.CREATED);
    }

    /**
     * @Method : findBoardsByAccountId
     * @Description : 게시한 게시글 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : ResponseEntity<SliceResult<BoardSummaryResponse>>
     **/
    @ApiOperation(value="내가 게시한 게시글 조회")
    @GetMapping("/boards")
    public ResponseEntity<SliceResult<BoardSummaryResponse>> findBoardsByAccountId(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "accountId", required = true)
            @PathVariable Long accountId,
            @ApiParam(value = "pageable", required = false)
            Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getSliceResult(accountInfoService.findBoardsByAccountId(accountId, pageable)), HttpStatus.CREATED);
    }
}