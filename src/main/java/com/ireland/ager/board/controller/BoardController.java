package com.ireland.ager.board.controller;

import com.ireland.ager.board.dto.request.BoardRequest;
import com.ireland.ager.board.dto.response.BoardResponse;
import com.ireland.ager.board.service.BoardServiceImpl;
import com.ireland.ager.main.common.CommonResult;
import com.ireland.ager.main.common.SingleResult;
import com.ireland.ager.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/board")
@CrossOrigin(value = {"*"}, maxAge = 6000)
public class BoardController {

    private final ResponseService responseService;
    private final BoardServiceImpl boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity<SingleResult<BoardResponse>> findBoardById(@PathVariable Long boardId,
                                                                     @RequestHeader("Authorization") String accessToken) {

        String[] splitToken = accessToken.split(" ");
        BoardResponse boardResponse = boardService.findPostById(splitToken[1], boardId);
        return new ResponseEntity<>(responseService.getSingleResult(boardResponse), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SingleResult<BoardResponse>> createPost(@RequestHeader("Authorization") String accessToken,
                                                                  @RequestPart(value = "board") BoardRequest boardRequest) throws IOException {

        String[] splitToken = accessToken.split(" ");
        BoardResponse boardResponse = boardService.createPost(splitToken[1], boardRequest);
        return new ResponseEntity<>(responseService.getSingleResult(boardResponse), HttpStatus.CREATED);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<SingleResult<BoardResponse>> updatePost(@RequestHeader("Authorization") String accessToken,
                                                                  @RequestPart(value = "board") BoardRequest boardRequest,
                                                                  @PathVariable(value = "boardId") Long boardId) throws IOException {
        String[] splitToken = accessToken.split(" ");
        BoardResponse boardResponse = boardService.updatePost(splitToken[1], boardId, boardRequest);
        return new ResponseEntity<>(responseService.getSingleResult(boardResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResult> deletePost(@RequestHeader("Authorization") String accessToken,
                                                   @PathVariable(value = "boardId") Long boardId) throws IOException {
        String[] splitToken = accessToken.split(" ");
        boardService.deletePost(splitToken[1], boardId);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
}