package com.ireland.ager.chat.dto.response;

import com.ireland.ager.chat.entity.MessageRoom;

/**
 * @Class : MessageDetailsResponse
 * @Description : 메세지도메인에 대한 Response DTO
 **/
public class MessageDetailsResponse {
    Long roomId;
    String reviewStatus;

    MessageDetailsResponse(Long roomId, String reviewStatus) {
        this.roomId = roomId;
        this.reviewStatus = reviewStatus;
    }

    /**
     * @Method : toMessageDetailsResponse
     * @Description : 메세지 상세정보 데이터 응답객체화
     * @Parameter : [messageRoom]
     * @Return : MessageDetailsResponse
     **/
    public static MessageDetailsResponse toMessageDetailsResponse(MessageRoom messageRoom) {
        return MessageDetailsResponse.builder()
                .roomId(messageRoom.getRoomId())
                .reviewStatus(messageRoom.getReviewStatus().name())
                .build();
    }

    public static MessageDetailsResponseBuilder builder() {
        return new MessageDetailsResponseBuilder();
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public String getReviewStatus() {
        return this.reviewStatus;
    }

    public static class MessageDetailsResponseBuilder {
        private Long roomId;
        private String reviewStatus;

        MessageDetailsResponseBuilder() {
        }

        public MessageDetailsResponseBuilder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public MessageDetailsResponseBuilder reviewStatus(String reviewStatus) {
            this.reviewStatus = reviewStatus;
            return this;
        }

        public MessageDetailsResponse build() {
            return new MessageDetailsResponse(roomId, reviewStatus);
        }

        public String toString() {
            return "MessageDetailsResponse.MessageDetailsResponseBuilder(roomId=" + this.roomId + ", reviewStatus=" + this.reviewStatus + ")";
        }
    }
}