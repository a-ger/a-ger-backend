package com.ireland.ager.board.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.BoardUrl;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Class : BoardRequest
 * @Description : 게시판 도메인에 대한 Request DTO
 **/
public class BoardRequest {

    @NotBlank(message = "3110")
    String title;

    @NotBlank(message = "3130")
    String content;

    public BoardRequest() {
    }

    /**
     * @Method : toBoard
     * @Description : 게시판 데이터 객체화
     * @Parameter : [boardRequest, account, uploadImgUrl]
     * @Return : Board
     **/
    public static Board toBoard(BoardRequest boardRequest, Account account, List<String> uploadImgUrl) {
        Board board = new Board();

        for (String str : uploadImgUrl) {
            BoardUrl url = new BoardUrl();
            url.setUrl(str);
            board.addUrl(url);
        }
        board.addAccount(account);
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setBoardViewCnt(0L);
        return board;
    }

    /**
     * @Method : toBoardUpdate
     * @Description : 게시판 정보 수정 데이터 객체화
     * @Parameter : [board, uploadImageUrl]
     * @Return : Board
     **/
    public Board toBoardUpdate(Board board,
                               List<String> uploadImageUrl) {
        for (String str : uploadImageUrl) {
            BoardUrl url = new BoardUrl();
            url.setUrl(str);
            board.addUrl(url);
        }
        board.setTitle(this.title);
        board.setContent(this.content);
        return board;
    }

    public @NotBlank(message = "3110") String getTitle() {
        return this.title;
    }

    public @NotBlank(message = "3130") String getContent() {
        return this.content;
    }

    public void setTitle(@NotBlank(message = "3110") String title) {
        this.title = title;
    }

    public void setContent(@NotBlank(message = "3130") String content) {
        this.content = content;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BoardRequest)) return false;
        final BoardRequest other = (BoardRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BoardRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        return result;
    }

    public String toString() {
        return "BoardRequest(title=" + this.getTitle() + ", content=" + this.getContent() + ")";
    }
}
