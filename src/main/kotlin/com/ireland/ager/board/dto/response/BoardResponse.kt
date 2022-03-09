package com.ireland.ager.board.dto.response

import com.ireland.ager.account.dto.response.MyAccountResponse
import com.ireland.ager.account.entity.Account
import com.ireland.ager.board.entity.Board
import com.ireland.ager.board.entity.BoardUrl
import java.time.LocalDateTime

lombok.Builderimport lombok.Dataimport java.time.LocalDateTime
/**
 * @Class : BoardResponse
 * @Description : 게시판 도메인에 대한 Response DTO
 */
data class BoardResponse {
    var boardId: Long? = null
    var title: String? = null
    var content: String? = null
    var boardViewCnt: Long? = null
    var urlList: List<BoardUrl>? = null
    var createAt: LocalDateTime? = null
    var updateAt: LocalDateTime? = null
    var isOwner = false

    companion object {
        /**
         * @Method : toBoardResponse
         * @Description : 게시판 데이터 응답 객체화
         * @Parameter : [board, account]
         * @Return : BoardResponse
         */
        fun toBoardResponse(board: Board?, account: Account?): BoardResponse {
            return BoardResponse
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .boardViewCnt(board.getBoardViewCnt())
                .urlList(board.getUrlList())
                .createAt(board.getCreatedAt())
                .updateAt(board.getUpdatedAt())
                .isOwner(board.getAccountId() == account)
                .build()
        }
        fun builder(): BoardResponse.BoardResponseBuilder {
            return MyAccountResponse.MyAccountResponseBuilder()
        }
    }
}