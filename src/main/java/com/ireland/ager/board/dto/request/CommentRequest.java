package com.ireland.ager.board.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.Comment;

/**
 * @Class : CommentRequest
 * @Description : 댓글 도메인에 대한 Request DTO
 **/
public class CommentRequest {

    String commentContent;

    public CommentRequest() {
    }

    /**
     * @Method : toComment
     * @Description : 댓글 데이터 객체화
     * @Parameter : [commentRequest, board, account]
     * @Return : Comment
     **/
    public static Comment toComment(CommentRequest commentRequest, Board board, Account account) {
        Comment comment = new Comment();
        comment.addAccount(account);
        comment.setBoardId(board);
        comment.setCommentContent(commentRequest.getCommentContent());
        return comment;
    }

    public String getCommentContent() {
        return this.commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CommentRequest)) return false;
        final CommentRequest other = (CommentRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$commentContent = this.getCommentContent();
        final Object other$commentContent = other.getCommentContent();
        if (this$commentContent == null ? other$commentContent != null : !this$commentContent.equals(other$commentContent))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CommentRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $commentContent = this.getCommentContent();
        result = result * PRIME + ($commentContent == null ? 43 : $commentContent.hashCode());
        return result;
    }

    public String toString() {
        return "CommentRequest(commentContent=" + this.getCommentContent() + ")";
    }
}
