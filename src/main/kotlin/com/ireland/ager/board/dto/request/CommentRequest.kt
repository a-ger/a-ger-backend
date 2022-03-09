package com.ireland.ager.board.dto.request

import com.ireland.ager.account.entity.Account
import com.ireland.ager.board.entity.Board
import com.ireland.ager.board.entity.Comment

/**
 * @Class : CommentRequest
 * @Description : 댓글 도메인에 대한 Request DTO
 */
data class CommentRequest(
    var commentContent: String
) {
    companion object {
        /**
         * @Method : toComment
         * @Description : 댓글 데이터 객체화
         * @Parameter : [commentRequest, board, account]
         * @Return : Comment
         */
        fun toComment(commentRequest: CommentRequest, board: Board?, account: Account?): Comment {
            val comment = Comment()
            comment.addAccount(account)
            comment.boardId = board
            comment.commentContent = commentRequest.commentContent
            return comment
        }
    }
}