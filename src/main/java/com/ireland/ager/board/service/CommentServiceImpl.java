package com.ireland.ager.board.service;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.exception.UnAuthorizedAccessException;
import com.ireland.ager.account.service.AccountServiceImpl;
import com.ireland.ager.board.dto.request.CommentRequest;
import com.ireland.ager.board.dto.response.CommentResponse;
import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.Comment;
import com.ireland.ager.board.repository.BoardRepository;
import com.ireland.ager.board.repository.CommentRepository;
import com.ireland.ager.main.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

/**
 * @Class : CommentServiceImpl
 * @Description : 댓글 도메인에 대한 서비스
 **/
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final AccountServiceImpl accountService;

    /**
     * @Method : saveComment
     * @Description : 댓글 저장
     * @Parameter : [accessToken, boardId, commentRequest]
     * @Return : CommentResponse
     **/
    public CommentResponse saveComment(String accessToken, Long boardId, CommentRequest commentRequest) {
        Board board = boardRepository.findById(boardId).orElseThrow(NotFoundException::new);
        Account account = accountService.findAccountByAccessToken(accessToken);
        Comment newComment = commentRepository.save(CommentRequest.toComment(commentRequest, board, account));
        return CommentResponse.toCommentResponse(newComment);
    }

    /**
     * @Method : updateComment
     * @Description : 댓글 수정
     * @Parameter : [accessToken, boardId, commentRequest]
     * @Return : CommentResponse
     **/
    public CommentResponse updateComment(String accessToken, Long commentId, CommentRequest commentRequest) {
        Comment presentComment = commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
        Account account = accountService.findAccountByAccessToken(accessToken);
        if (!account.equals(presentComment.getAccountId())) {
            throw new UnAuthorizedAccessException();
        }
        Comment updateComment = commentRepository.save(CommentRequest.toComment(commentRequest, presentComment.getBoardId(), account));
        return CommentResponse.toCommentResponse(updateComment);
    }

    /**
     * @Method : deleteComment
     * @Description : 댓글 삭제
     * @Parameter : [accessToken, commentId]
     * @Return : null
     **/
    public void deleteComment(String accessToken, Long commentId) throws IOException {
        Comment presentComment = commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
        Account account = accountService.findAccountByAccessToken(accessToken);
        if (!account.equals(presentComment.getAccountId())) {
            throw new UnAuthorizedAccessException();
        }
        commentRepository.deleteById(presentComment.getCommentId());
    }
}
