package com.ireland.ager.chat.dto.response;

import com.ireland.ager.chat.entity.MessageRoom;

/**
 * @Class : RoomCreateResponse
 * @Description : 메세지도메인에 대한 RoomResponse DTO
 **/
public class RoomCreateResponse {
    Long roomId;

    RoomCreateResponse(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * @Method : toRoomCreateResponse
     * @Description : 메세지룸 데이터 응답 객체화
     * @Parameter :  [messageRoom]
     * @Return : RoomCreateResponse
     **/
    public static RoomCreateResponse toRoomCreateResponse(MessageRoom messageRoom) {
        return RoomCreateResponse.builder()
                .roomId(messageRoom.getRoomId())
                .build();
    }

    public static RoomCreateResponseBuilder builder() {
        return new RoomCreateResponseBuilder();
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public static class RoomCreateResponseBuilder {
        private Long roomId;

        RoomCreateResponseBuilder() {
        }

        public RoomCreateResponseBuilder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public RoomCreateResponse build() {
            return new RoomCreateResponse(roomId);
        }

        public String toString() {
            return "RoomCreateResponse.RoomCreateResponseBuilder(roomId=" + this.roomId + ")";
        }
    }
}
