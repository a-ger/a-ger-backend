package com.ireland.ager.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class : BoardUrl
 * @Description : 게시판 이미지 Url 도메인에 대한 엔티티
 **/
@Entity
@DynamicUpdate
public class BoardUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardUrlId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "board_id")
    private Board board;

    private String url;

    public BoardUrl(Long boardUrlId, Board board, String url) {
        this.boardUrlId = boardUrlId;
        this.board = board;
        this.url = url;
    }

    public BoardUrl() {
    }

    public static BoardUrlBuilder builder() {
        return new BoardUrlBuilder();
    }

    public Long getBoardUrlId() {
        return this.boardUrlId;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getUrl() {
        return this.url;
    }

    public void setBoardUrlId(Long boardUrlId) {
        this.boardUrlId = boardUrlId;
    }

    @JsonIgnore
    public void setBoard(Board board) {
        this.board = board;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BoardUrl)) return false;
        final BoardUrl other = (BoardUrl) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$boardUrlId = this.getBoardUrlId();
        final Object other$boardUrlId = other.getBoardUrlId();
        if (this$boardUrlId == null ? other$boardUrlId != null : !this$boardUrlId.equals(other$boardUrlId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BoardUrl;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $boardUrlId = this.getBoardUrlId();
        result = result * PRIME + ($boardUrlId == null ? 43 : $boardUrlId.hashCode());
        return result;
    }

    public static class BoardUrlBuilder {
        private Long boardUrlId;
        private Board board;
        private String url;

        BoardUrlBuilder() {
        }

        public BoardUrlBuilder boardUrlId(Long boardUrlId) {
            this.boardUrlId = boardUrlId;
            return this;
        }

        public BoardUrlBuilder board(Board board) {
            this.board = board;
            return this;
        }

        public BoardUrlBuilder url(String url) {
            this.url = url;
            return this;
        }

        public BoardUrl build() {
            return new BoardUrl(boardUrlId, board, url);
        }

        public String toString() {
            return "BoardUrl.BoardUrlBuilder(boardUrlId=" + this.boardUrlId + ", board=" + this.board + ", url=" + this.url + ")";
        }
    }
}
