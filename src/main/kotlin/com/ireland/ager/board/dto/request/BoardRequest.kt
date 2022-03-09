package com.ireland.ager.board.dto.request

import com.ireland.ager.account.entity.Account
import com.ireland.ager.board.entity.Board
import com.ireland.ager.board.entity.BoardUrl
import javax.validation.constraints.NotBlank

/**
 * @Class : BoardRequest
 * @Description : 게시판 도메인에 대한 Request DTO
 */
data class BoardRequest(
    var title: @NotBlank(message = "3110") String,
    var content: @NotBlank(message = "3130") String
) {
    /**
     * @Method : toBoardUpdate
     * @Description : 게시판 정보 수정 데이터 객체화
     * @Parameter : [board, uploadImageUrl]
     * @Return : Board
     */
    fun toBoardUpdate(
        board: Board?,
        uploadImageUrl: List<String?>?
    ): Board? {
        for (str in uploadImageUrl!!) {
            val url = BoardUrl()
            url.url = str
            board!!.addUrl(url)
        }
        board?.title=title
        board?.content=content
        return board
    }
    companion object {
        /**
         * @Method : toBoard
         * @Description : 게시판 데이터 객체화
         * @Parameter : [boardRequest, account, uploadImgUrl]
         * @Return : Board
         */
        fun toBoard(boardRequest: BoardRequest?, account: Account?, uploadImgUrl: List<String?>?): Board {
            val board = Board()
            for (str in uploadImgUrl!!) {
                val url = BoardUrl()
                url.url = str
                board.addUrl(url)
            }
            board.addAccount(account)
            board.title = boardRequest!!.title
            board.content = boardRequest.content
            board.boardViewCnt = 0L
            return board
        }
    }
}