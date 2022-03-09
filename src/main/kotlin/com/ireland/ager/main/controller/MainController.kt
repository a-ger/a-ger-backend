package com.ireland.ager.main.controller

import com.ireland.ager.board.dto.response.BoardSummaryResponse
import com.ireland.ager.board.service.BoardServiceImpl
import com.ireland.ager.main.common.ListResult
import com.ireland.ager.main.common.SliceResult
import com.ireland.ager.main.common.service.ResponseService
import com.ireland.ager.main.service.SearchService
import com.ireland.ager.product.dto.response.ProductThumbResponse
import com.ireland.ager.product.entity.*
import com.ireland.ager.product.service.ProductServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @Class : MainController
 * @Description : 메인 도메인에 대한 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = ["*"], maxAge = 6000)
class MainController {
    private val productService: ProductServiceImpl? = null
    private val boardService: BoardServiceImpl? = null
    private val responseService: ResponseService? = null
    private val searchService: SearchService? = null

    /**
     * @Method : searchAllProducts
     * @Description : 상품 검색
     * @Parameter : [accessToken, category, keyword, pageable]
     * @Return : ResponseEntity<SliceResult></SliceResult><ProductThumbResponse>>
    </ProductThumbResponse> */
    @ApiOperation(value = "상품 검색")
    @GetMapping("/api/product/search")
    fun searchAllProducts(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "category", required = false) @RequestParam(
            value = "category",
            required = false
        ) category: Category?,
        @ApiParam(value = "keyword", required = false) @RequestParam(
            value = "keyword",
            required = false
        ) keyword: String?,
        @ApiParam(value = "pageable", required = false) pageable: Pageable?
    ): ResponseEntity<SliceResult<ProductThumbResponse>> {
        val splitToken = accessToken.split(" ").toTypedArray()
        searchService!!.postKeyword(splitToken[1], keyword)
        return ResponseEntity(
            responseService!!.getSliceResult(productService!!.findProductAllByCreatedAtDesc(category, keyword, pageable)!!), HttpStatus.OK
        )
    }

    /**
     * @Method : searchAllBoards
     * @Description : 게시물 검색
     * @Parameter : [accessToken, keyword, pageable]
     * @Return : ResponseEntity<SliceResult></SliceResult><BoardSummaryResponse>>
    </BoardSummaryResponse> */
    @ApiOperation(value = "게시물 검색")
    @GetMapping("/api/board/search")
    fun searchAllBoards(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String?,
        @ApiParam(value = "keyword", required = false) @RequestParam(
            value = "keyword",
            required = false
        ) keyword: String?,
        @ApiParam(value = "pageable", required = false) pageable: Pageable?
    ): ResponseEntity<SliceResult<BoardSummaryResponse?>?> {
        return ResponseEntity(
            responseService!!.getSliceResult(
                boardService!!.findBoardAllByCreatedAtDesc(keyword, pageable)
            ), HttpStatus.OK
        )
    }

    /**
     * @Method : searchAccountKeywords
     * @Description : 최근 키워드 조회
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<ListResult></ListResult><String>>
    </String> */
    @ApiOperation(value = "계정의 최근 키워드 5개 조회")
    @GetMapping("/api/recent-keyword")
    fun searchAccountKeywords(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String
    ): ResponseEntity<ListResult<String>> {
        val splitToken = accessToken.split(" ").toTypedArray()
        return ResponseEntity(
            responseService!!.getListResult(
                searchService!!.getSearchList(splitToken[1])
            ), HttpStatus.OK
        )
    }

    /**
     * @Method : searchPopularKeywords
     * @Description : 인기 키워드 조회
     * @Parameter : [accessToken]
     * @Return : ResponseEntity<ListResult></ListResult><String>>
    </String> */
    @ApiOperation(value = "일일 인기 키워드 5개 조회")
    @GetMapping("/api/popular-keyword")
    fun searchPopularKeywords(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<ListResult<String?>?> {
        return ResponseEntity(
            responseService!!.getListResult(
                searchService!!.popularSearchList
            ), HttpStatus.OK
        )
    }
}