package com.ireland.ager.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class : Comment
 * @Description : 댓글 도메인에 대한 엔티티
 **/
@Entity
public class Comment extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board boardId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account accountId;

    private String commentContent;

    public Comment(Long commentId, Board boardId, Account accountId, String commentContent) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.accountId = accountId;
        this.commentContent = commentContent;
    }

    public Comment() {
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    /**
     * @Method : addAccount
     * @Description : 계정 정보 추가
     * @Parameter : [account]
     * @Return : null
     **/
    public void addAccount(Account account) {
        this.accountId = account;
    }

    public Long getCommentId() {
        return this.commentId;
    }

    public Board getBoardId() {
        return this.boardId;
    }

    public Account getAccountId() {
        return this.accountId;
    }

    public String getCommentContent() {
        return this.commentContent;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public void setBoardId(Board boardId) {
        this.boardId = boardId;
    }

    @JsonIgnore
    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public static class CommentBuilder {
        private Long commentId;
        private Board boardId;
        private Account accountId;
        private String commentContent;

        CommentBuilder() {
        }

        public CommentBuilder commentId(Long commentId) {
            this.commentId = commentId;
            return this;
        }

        public CommentBuilder boardId(Board boardId) {
            this.boardId = boardId;
            return this;
        }

        public CommentBuilder accountId(Account accountId) {
            this.accountId = accountId;
            return this;
        }

        public CommentBuilder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        public Comment build() {
            return new Comment(commentId, boardId, accountId, commentContent);
        }

        public String toString() {
            return "Comment.CommentBuilder(commentId=" + this.commentId + ", boardId=" + this.boardId + ", accountId=" + this.accountId + ", commentContent=" + this.commentContent + ")";
        }
    }
}
