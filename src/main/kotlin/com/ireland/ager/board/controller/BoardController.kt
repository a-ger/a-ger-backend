package com.ireland.ager.board.controller

import com.ireland.ager.account.service.AccountServiceImpl
import com.ireland.ager.board.dto.request.BoardRequest
import com.ireland.ager.board.dto.response.BoardResponse
import com.ireland.ager.board.service.BoardServiceImpl
import com.ireland.ager.main.common.CommonResult
import com.ireland.ager.main.common.SingleResult
import com.ireland.ager.main.common.service.ResponseService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import javax.validation.Valid

/**
 * @Class : BoardController
 * @Description : 게시판 도메인에 대한 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/board")
@CrossOrigin(value = ["*"], maxAge = 6000)
open class BoardController {
    private val responseService: ResponseService? = null
    private val boardService: BoardServiceImpl? = null
    private val accountService: AccountServiceImpl? = null

    /**
     * @Method : findBoardById
     * @Description : 회원 아이디로 게시물을 조회
     * @Parameter : [boardId, accessToken]
     * @Return : ResponseEntity<SingleResult></SingleResult><BoardResponse>>
    </BoardResponse> */
    @ApiOperation(value = "게시물 조회")
    @GetMapping("/{boardId}")
    fun findBoardById(
        @ApiParam(value = "boardId", required = true) @PathVariable boardId: Long,
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String
    ): ResponseEntity<SingleResult<BoardResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        val account = accountService!!.findAccountByAccessToken(splitToken[1])
        val board = boardService!!.findPostById(boardId)
        boardService.addViewCntToRedis(boardId)
        return ResponseEntity(
            responseService!!.getSingleResult(BoardResponse.toBoardResponse(board, account)), HttpStatus.OK
        )
    }

    /**
     * @Method : createPost
     * @Description : 게시물 생성
     * @Parameter : [accessToken, multipartFile, boardRequest, bindingResult]
     * @Return : ResponseEntity<SingleResult></SingleResult><BoardResponse>>
    </BoardResponse> */
    @ApiOperation(value = "게시물 등록")
    @PostMapping
    @Throws(IOException::class)
    fun createPost(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "게시물 사진", required = false) @RequestPart(
            value = "file",
            required = false
        ) multipartFile: List<MultipartFile>,
        @ApiParam(value = "게시물 DTO", required = true) @RequestPart(value = "board") boardRequest: @Valid BoardRequest?,
        bindingResult: BindingResult
    ): ResponseEntity<SingleResult<BoardResponse?>?> {
        boardService!!.validateUploadForm(bindingResult)
        boardService.validateFileExists(multipartFile)
        val splitToken = accessToken.split(" ").toTypedArray()
        val boardResponse = boardService.createPost(splitToken[1], boardRequest, multipartFile)
        return ResponseEntity(responseService!!.getSingleResult(boardResponse), HttpStatus.CREATED)
    }

    /**
     * @Method : updatePost
     * @Description : 게시물 수정
     * @Parameter : [accessToken, boardRequest, multipartFile, boardId]
     * @Return : ResponseEntity<SingleResult></SingleResult><BoardResponse>>
    </BoardResponse> */
    @ApiOperation(value = "게시물 수정")
    @PatchMapping("/{boardId}")
    @Throws(IOException::class)
    fun updatePost(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "게시물 DTO", required = true) @RequestPart(value = "board") boardRequest: BoardRequest,
        @ApiParam(value = "게시물 사진", required = false) @RequestPart(
            value = "file",
            required = false
        ) multipartFile: List<MultipartFile>,
        @ApiParam(value = "boardId", required = true) @PathVariable(value = "boardId") boardId: Long?
    ): ResponseEntity<SingleResult<BoardResponse?>?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        val boardResponse = boardService!!.updatePost(splitToken[1], boardId, boardRequest, multipartFile)
        return ResponseEntity(responseService!!.getSingleResult(boardResponse), HttpStatus.OK)
    }

    /**
     * @Method : deletePost
     * @Description : 게시물 삭제
     * @Parameter : [accessToken, boardId]
     * @Return : ResponseEntity<CommonResult>
    </CommonResult> */
    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping("/{boardId}")
    @Throws(IOException::class)
    fun deletePost(
        @ApiParam(value = "액세스 토큰", required = true) @RequestHeader("Authorization") accessToken: String,
        @ApiParam(value = "boardId", required = true) @PathVariable(value = "boardId") boardId: Long
    ): ResponseEntity<CommonResult?> {
        val splitToken = accessToken.split(" ").toTypedArray()
        boardService!!.deletePost(splitToken[1], boardId)
        return ResponseEntity(responseService?.successResult, HttpStatus.OK)
    }
}