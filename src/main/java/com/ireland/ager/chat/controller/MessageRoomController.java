package com.ireland.ager.chat.controller;

import com.ireland.ager.account.service.AuthServiceImpl;
import com.ireland.ager.chat.dto.response.MessageDetailsResponse;
import com.ireland.ager.chat.dto.response.MessageSummaryResponse;
import com.ireland.ager.chat.dto.response.RoomCreateResponse;
import com.ireland.ager.chat.service.MessageService;
import com.ireland.ager.main.common.CommonResult;
import com.ireland.ager.main.common.SingleResult;
import com.ireland.ager.main.common.SliceResult;
import com.ireland.ager.main.common.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : MessageRoomController
 * @Description : 메세지룸도메인에 대한 컨트롤러
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class MessageRoomController {
    private final MessageService messageService;
    private final ResponseService responseService;

    /**
     * @Method : searchAllRoomList
     * @Description : 채팅방 리스트 조회
     * @Parameter : [accessToken, pageable]
     * @Return : ResponseEntity<SliceResult<MessageSummaryResponse>>
     **/
    @GetMapping
    @ApiOperation(value = "유저별 모든 채팅방 리스트 조회")
    public ResponseEntity<SliceResult<MessageSummaryResponse>> searchAllRoomList(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken
            , Pageable pageable) {
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getSliceResult(
                messageService.findRoomByAccessToken(splitToken[1], pageable)), HttpStatus.OK);
    }

    /**
     * @Method : roomEnter
     * @Description : 메세지룸 접속
     * @Parameter : [roomId, accessToken]
     * @Return : ResponseEntity<SingleResult<MessageDetailsResponse>>
     **/
    @GetMapping("/{roomId}")
    @ApiOperation(value = "채팅방 입장하기")
    public ResponseEntity<SingleResult<MessageDetailsResponse>> roomEnter(
            @ApiParam(value = "채팅방 아이디", required = true)
            @PathVariable Long roomId,
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        String[] splitToken = accessToken.split(" ");
        MessageDetailsResponse messageRoom = messageService.roomEnterByAccessToken(splitToken[1], roomId);
        return new ResponseEntity<>(responseService.getSingleResult(messageRoom), HttpStatus.OK);
    }

    /**
     * @Method : insertRoom
     * @Description : 메세지룸 생성
     * @Parameter : [productId, accessToken]
     * @Return : ResponseEntity<SingleResult<RoomCreateResponse>>
     **/
    @PostMapping("/{productId}")
    @ApiOperation(value = "채팅방 생성")
    public ResponseEntity<SingleResult<RoomCreateResponse>> insertRoom(
            @ApiParam(value = "등록된 제품 아이디", required = true)
            @PathVariable Long productId,
            @ApiParam(value = "엑세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        String[] splitToken = accessToken.split(" ");
        RoomCreateResponse roomCreateResponse = messageService.insertRoom(productId, splitToken[1]);
        return new ResponseEntity<>(responseService.getSingleResult(roomCreateResponse), HttpStatus.CREATED);
    }

    /**
     * @Method : roomDelete
     * @Description : 메세지룸 삭제
     * @Parameter : [roomId, accessToken]
     * @Return : ResponseEntity<CommonResult>
     **/
    @DeleteMapping("/{roomId}")
    @ApiOperation(value = "채팅방 삭제")
    public ResponseEntity<CommonResult> roomDelete(
            @ApiParam(value = "채팅방 아이디", required = true)
            @PathVariable Long roomId,
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        String[] splitToken = accessToken.split(" ");
        messageService.deleteById(splitToken[1], roomId);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
}
