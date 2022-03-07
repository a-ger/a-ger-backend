package com.ireland.ager.chat.controller;

import com.ireland.ager.chat.dto.request.MessageRequest;
import com.ireland.ager.chat.entity.Message;
import com.ireland.ager.chat.service.KafkaProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

/**
 * @Class : MessageController
 * @Description : 메세지도메인에 대한 컨트롤러
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class MessageController {
    private final KafkaProductService kafkaProductService;

    /**
     * @Method : sendMessage
     * @Description : 카프카 프로듀서에게 메세지 전달
     * @Parameter : [message]
     * @Return : void
     **/
    @PostMapping(value = "/publish")
    @ApiOperation(value = "메시지 전송")
    public void sendMessage(
            @ApiParam(value = "메시지 내용", required = true)
            @RequestBody MessageRequest message) {
        kafkaProductService.sendMessage(message);
    }

    /**
     * @Method : broadcastGroupMessage
     * @Description : 프론트엔드로 메세지 전송
     * @Parameter : [roomId, message]
     * @Return : Message
     **/
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group/{roomId}")
    @ApiOperation(value = "특정 채딩방 접속")
    public Message broadcastGroupMessage(
            @ApiParam(value = "채팅방 아이디", required = true)
            @DestinationVariable Long roomId,
            @ApiParam(value = "메시지", required = true)
            @Payload Message message) {
        return message;
    }
}