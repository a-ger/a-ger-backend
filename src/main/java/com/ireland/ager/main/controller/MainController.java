package com.ireland.ager.main.controller;

import com.ireland.ager.board.dto.response.BoardSummaryResponse;
import com.ireland.ager.board.service.BoardServiceImpl;
import com.ireland.ager.main.common.ListResult;
import com.ireland.ager.main.common.SliceResult;
import com.ireland.ager.main.common.service.ResponseService;
import com.ireland.ager.main.service.SearchService;
import com.ireland.ager.product.dto.response.ProductThumbResponse;
import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.service.ProductServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : MainController
 * @Description : 메인 도메인에 대한 컨트롤러
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = {"*"}, maxAge = 6000)
public class MainController {

    private final ProductServiceImpl productService;
    private final BoardServiceImpl boardService;
    private final ResponseService responseService;
    private final SearchService searchService;

    /**
     * @Method : searchAllProducts
     * @Description : 상품 검색
     * @Parameter : [accessToken, category, keyword, pageable]
     * @Return : ResponseEntity<SliceResult<ProductThumbResponse>>
     **/
    @ApiOperation(value="상품 검색")
    @GetMapping("/api/product/search")
    public ResponseEntity<SliceResult<ProductThumbResponse>> searchAllProducts(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "category", required = false)
            @RequestParam(value = "category", required = false) Category category,
            @ApiParam(value = "keyword", required = false)
            @RequestParam(value = "keyword", required = false) String keyword,
            @ApiParam(value = "pageable", required = false)
            Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        searchService.postKeyword(splitToken[1], keyword);
        return new ResponseEntity<>(responseService.getSliceResult(
                productService.findProductAllByCreatedAtDesc(category, keyword, pageable)), HttpStatus.OK);
    }

    /**
     * @Method : searchAllBoards
     * @Description : 게시물 검색
     * @Parameter : [accessToken, keyword, pageable]
     * @Return : ResponseEntity<SliceResult<BoardSummaryResponse>>
     **/
    @ApiOperation(value="게시물 검색")
    @GetMapping("/api/board/search")
    public ResponseEntity<SliceResult<BoardSummaryResponse>> searchAllBoards(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "keyword", required = false)
            @RequestParam(value = "keyword", required = false) String keyword,
            @ApiParam(value = "pageable", required = false)
            Pageable pageable) {
        return new ResponseEntity<>(responseService.getSliceResult(
                boardService.findBoardAllByCreatedAtDesc(keyword, pageable)), HttpStatus.OK);
    }

    /**
     * @Method : searchAccountKeywords
     * @Description : 최근 키워드 조회
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<ListResult<String>>
     **/
    @ApiOperation(value="계정의 최근 키워드 5개 조회")
    @GetMapping("/api/recent-keyword")
    public ResponseEntity<ListResult<String>> searchAccountKeywords(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getListResult(
                searchService.getSearchList(splitToken[1])), HttpStatus.OK);
    }

    /**
     * @Method : searchPopularKeywords
     * @Description : 인기 키워드 조회
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<ListResult<String>>
     **/
    @ApiOperation(value="일일 인기 키워드 5개 조회")
    @GetMapping("/api/popular-keyword")
    public ResponseEntity<ListResult<String>> searchPopularKeywords(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken) {
        return new ResponseEntity<>(responseService.getListResult(
                searchService.getPopularSearchList()), HttpStatus.OK);
    }
}
