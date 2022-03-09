package com.ireland.ager.account.service

import com.ireland.ager.account.exception.UnAuthorizedAccessException
import com.ireland.ager.board.dto.response.BoardSummaryResponse
import com.ireland.ager.board.repository.BoardRepository
import com.ireland.ager.product.dto.response.ProductThumbResponse
import com.ireland.ager.product.repositoryimport.ProductRepository
import com.ireland.ager.review.dto.response.ReviewResponse
import com.ireland.ager.review.repository.ReviewRepository
import com.ireland.ager.trade.repository.TradeRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Class : AccountInfoServiceImpl
 * @Description : 계정 도메인에 대한 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
open class AccountInfoServiceImpl {
    private val accountService: AccountServiceImpl? = null
    private val productRepository: ProductRepository? = null
    private val reviewRepository: ReviewRepository? = null
    private val boardRepository: BoardRepository? = null
    private val tradeRepository: TradeRepository? = null

    /**
     * @Method : findSellsByAccountId
     * @Description : 판매 내역 조회
     * @Parameter : [accountId, pageable]
     * @Return : Slice<ProductThumbResponse>
    </ProductThumbResponse> */
    fun findSellsByAccountId(accountId: Long, pageable: Pageable): Slice<ProductThumbResponse> {
        return productRepository!!.findSellProductsByAccountId(accountId, pageable)
    }

    /**
     * @Method : findBuysByACcountId
     * @Description : 구매 내역 조회
     * @Parameter : [accessToken, accountId, pageable]
     * @Return : Slice<ProductThumbResponse>
    </ProductThumbResponse> */
    fun findBuysByACcountId(accessToken: String?, accountId: Long, pageable: Pageable?): Slice<ProductThumbResponse?>? {
        val accountByAccessToken = accountService!!.findAccountByAccessToken(accessToken)
        if (accountByAccessToken?.accountId != accountId) {
            throw UnAuthorizedAccessException()
        }
        return tradeRepository!!.findBuyProductsByAccountId(accountId, pageable!!)
    }

    /**
     * @Method : findReviewsByAccountId
     * @Description : 받은 리뷰 내역 조회
     * @Parameter : [accountId, pageable]
     * @Return : Slice<ReviewResponse>
    </ReviewResponse> */
    fun findReviewsByAccountId(accountId: Long?, pageable: Pageable?): Slice<ReviewResponse> {
        return reviewRepository!!.findReviewsByAccountId(accountId, pageable!!)
    }

    /**
     * @Method : findBoardsByAccountId
     * @Description : 게시한 글 내역 조회
     * @Parameter : [accountId, pageable]
     * @Return : Slice<BoardSummaryResponse>
    </BoardSummaryResponse> */
    fun findBoardsByAccountId(accountId: Long?, pageable: Pageable?): Slice<BoardSummaryResponse> {
        return boardRepository!!.findBoardsByAccountId(accountId, pageable!!)
    }
}