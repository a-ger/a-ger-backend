package com.ireland.ager.board.dto.response

import com.ireland.ager.board.entity.Board
import java.time.LocalDateTime

/**
 * @Class : BoardSummaryResponse
 * @Description : 게시판 도메인에 대한 Response DTO
 */
@Builder
data class BoardSummaryResponse {
    var boardId: Long? = null
    var accountName: String? = null
    var title: String? = null
    var content: String? = null
    var boardViewCnt: Long? = null
    var countComment: Long? = null
    var createAt: LocalDateTime? = null

    companion object {
        /**
         * @Method : toBoardSummaryResponse
         * @Description : 게시판 정보 요약 데이터 응답 객체화
         * @Parameter : [board]
         * @Return : BoardSummaryResponse
         */
        fun toBoardSummaryResponse(board: Board): BoardSummaryResponse {
            return BoardSummaryResponse.builder()
                .boardId(board.boardId)
                .accountName(board.accountId?.userName)
                .title(board.title)
                .content(board.content)
                .boardViewCnt(board.boardViewCnt)
                .countComment(board.totalCommentCount)
                .createAt(board.createdAt)
                .build()
        }
    }
}