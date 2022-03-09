package com.ireland.ager.board.controller

import com.ireland.ager.board.dto.request.CommentRequest
import com.ireland.ager.board.dto.response.CommentResponse
import com.ireland.ager.board.repository.BoardRepository
import com.ireland.ager.board.service.BoardServiceImpl
import com.ireland.ager.board.service.CommentServiceImpl
import com.ireland.ager.main.common.CommonResult
import com.ireland.ager.main.common.SingleResult
import com.ireland.ager.main.common.service.ResponseService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException

/**
 * @Class : CommentController
 * @Description : 댓글 도메인에 대한 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
class CommentController {
    private val boardService: BoardServiceImpl? = null
    private val commentService: CommentServiceImpl? = null
    private val boardRepository: BoardRepository? = null
    private val responseService: ResponseService? = null

    /**
     * @Method : createComment
     * @Description : 댓글 등록
     * @Parameter : [accessToken, commentRequest, boardId]
     * @Return : ResponseEntity<SingleResult></SingleResult><CommentResponse>>
    </CommentResponse> */
    @ApiOperation(value = "댓글 등록")
    @PostMapping("/{boardId}/comments")
    @Throws(IOException::class)
    fun createComment(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "댓글 DTO", required = true) @RequestPart(value = "comments") commentRequest: CommentRequest,
        @ApiParam(value = "boardId", required = true) @PathVariable(value = "boardId") boardId: Long
    ): ResponseEntity<SingleResult<CommentResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        val commentResponse = commentService!!.saveComment(splitToken[1], boardId, commentRequest)
        return ResponseEntity(responseService!!.getSingleResult(commentResponse), HttpStatus.CREATED)
    }

    /**
     * @Method : updateComment
     * @Description : 댓글 수정
     * @Parameter : [accessToken, commentRequest, boardId, commentId]
     * @Return : ResponseEntity<SingleResult></SingleResult><CommentResponse>>
    </CommentResponse> */
    @ApiOperation(value = "댓글 수정")
    @PatchMapping("/{boardId}/comments/{commentId}")
    @Throws(IOException::class)
    fun updateComment(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "댓글 DTO", required = true) @RequestPart(value = "comments") commentRequest: CommentRequest,
        @ApiParam(value = "boardId", required = true) @PathVariable(value = "boardId") boardId: Long?,
        @ApiParam(value = "commentId", required = true) @PathVariable(value = "commentId") commentId: Long
    ): ResponseEntity<SingleResult<CommentResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        val commentResponse = commentService!!.updateComment(splitToken[1], commentId, commentRequest)
        return ResponseEntity(responseService!!.getSingleResult(commentResponse), HttpStatus.OK)
    }

    /**
     * @Method : deleteComment
     * @Description : 댓글 삭제
     * @Parameter : [accessToken, boardId, commentId]
     * @Return : ResponseEntity<CommonResult>
    </CommonResult> */
    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{boardId}/comments/{commentId}")
    @Throws(IOException::class)
    fun deleteComment(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "boardId", required = true) @PathVariable(value = "boardId") boardId: Long?,
        @ApiParam(value = "commentId", required = true) @PathVariable(value = "commentId") commentId: Long
    ): ResponseEntity<CommonResult?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        commentService!!.deleteComment(splitToken[1], commentId)
        return ResponseEntity(responseService?.successResult, HttpStatus.OK)
    }
}