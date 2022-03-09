package com.ireland.ager.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Class : Board
 * @Description : 게시판 도메인에 대한 엔티티
 **/
@Entity
public class Board extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account accountId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Long boardViewCnt;

    @Formula("(select count(1) from comment c where c.board_id=board_id)")
    private Long totalCommentCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board", orphanRemoval = true)
    private List<BoardUrl> urlList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "boardId", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<Comment>();

    public Board(Long boardId, Account accountId, String title, String content, Long boardViewCnt, Long totalCommentCount, List<BoardUrl> urlList, List<Comment> comments) {
        this.boardId = boardId;
        this.accountId = accountId;
        this.title = title;
        this.content = content;
        this.boardViewCnt = boardViewCnt;
        this.totalCommentCount = totalCommentCount;
        this.urlList = urlList;
        this.comments = comments;
    }

    public Board() {
    }

    public static BoardBuilder builder() {
        return new BoardBuilder();
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

    /**
     * @Method : addUrl
     * @Description : 이미지 Url 정보 추가
     * @Parameter : url
     * @Return : null
     **/
    public void addUrl(BoardUrl url) {
        this.getUrlList().add(url);
        url.setBoard(this);
    }

    /**
     * @Method : deleteUrl
     * @Description : 이미지 Url 삭제
     * @Parameter : []
     * @Return : null
     **/
    public void deleteUrl() {
        for (Iterator<BoardUrl> it = this.getUrlList().iterator(); it.hasNext(); ) {
            BoardUrl url = it.next();
            url.setBoard(null);
            it.remove();
        }
    }

    public Long getBoardId() {
        return this.boardId;
    }

    public Account getAccountId() {
        return this.accountId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Long getBoardViewCnt() {
        return this.boardViewCnt;
    }

    public Long getTotalCommentCount() {
        return this.totalCommentCount;
    }

    public List<BoardUrl> getUrlList() {
        return this.urlList;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    @JsonIgnore
    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBoardViewCnt(Long boardViewCnt) {
        this.boardViewCnt = boardViewCnt;
    }

    public void setTotalCommentCount(Long totalCommentCount) {
        this.totalCommentCount = totalCommentCount;
    }

    public void setUrlList(List<BoardUrl> urlList) {
        this.urlList = urlList;
    }

    @JsonIgnore
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class BoardBuilder {
        private Long boardId;
        private Account accountId;
        private String title;
        private String content;
        private Long boardViewCnt;
        private Long totalCommentCount;
        private List<BoardUrl> urlList;
        private List<Comment> comments;

        BoardBuilder() {
        }

        public BoardBuilder boardId(Long boardId) {
            this.boardId = boardId;
            return this;
        }

        public BoardBuilder accountId(Account accountId) {
            this.accountId = accountId;
            return this;
        }

        public BoardBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BoardBuilder content(String content) {
            this.content = content;
            return this;
        }

        public BoardBuilder boardViewCnt(Long boardViewCnt) {
            this.boardViewCnt = boardViewCnt;
            return this;
        }

        public BoardBuilder totalCommentCount(Long totalCommentCount) {
            this.totalCommentCount = totalCommentCount;
            return this;
        }

        public BoardBuilder urlList(List<BoardUrl> urlList) {
            this.urlList = urlList;
            return this;
        }

        public BoardBuilder comments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Board build() {
            return new Board(boardId, accountId, title, content, boardViewCnt, totalCommentCount, urlList, comments);
        }

        public String toString() {
            return "Board.BoardBuilder(boardId=" + this.boardId + ", accountId=" + this.accountId + ", title=" + this.title + ", content=" + this.content + ", boardViewCnt=" + this.boardViewCnt + ", totalCommentCount=" + this.totalCommentCount + ", urlList=" + this.urlList + ", comments=" + this.comments + ")";
        }
    }
}
