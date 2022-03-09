package com.ireland.ager.chat.dto.request;

import com.ireland.ager.chat.entity.Message;
import com.ireland.ager.chat.entity.MessageRoom;

import java.io.Serializable;

/**
 * @Class : MessageRequest
 * @Description : 메세지도메인에 대한 Request DTO
 **/
public class MessageRequest implements Serializable {
    private Long roomId;
    private Long senderId;
    private String message;

    public MessageRequest(Long roomId, Long senderId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
    }

    public MessageRequest() {
    }

    /**
     * @Method : toMessage
     * @Description : 메세지 정보 데이터 객체화
     * @Parameter :  [messageDto, messageRoom]
     * @Return : Message
     **/
    public static Message toMessage(MessageRequest messageDto, MessageRoom messageRoom) {
        return Message.builder()
                .message(messageDto.getMessage())
                .senderId(messageDto.getSenderId())
                .messageRoom(messageRoom)
                .build();
    }

    public static MessageRequestBuilder builder() {
        return new MessageRequestBuilder();
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MessageRequest)) return false;
        final MessageRequest other = (MessageRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$roomId = this.getRoomId();
        final Object other$roomId = other.getRoomId();
        if (this$roomId == null ? other$roomId != null : !this$roomId.equals(other$roomId)) return false;
        final Object this$senderId = this.getSenderId();
        final Object other$senderId = other.getSenderId();
        if (this$senderId == null ? other$senderId != null : !this$senderId.equals(other$senderId)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MessageRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $roomId = this.getRoomId();
        result = result * PRIME + ($roomId == null ? 43 : $roomId.hashCode());
        final Object $senderId = this.getSenderId();
        result = result * PRIME + ($senderId == null ? 43 : $senderId.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "MessageRequest(roomId=" + this.getRoomId() + ", senderId=" + this.getSenderId() + ", message=" + this.getMessage() + ")";
    }

    public static class MessageRequestBuilder {
        private Long roomId;
        private Long senderId;
        private String message;

        MessageRequestBuilder() {
        }

        public MessageRequestBuilder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public MessageRequestBuilder senderId(Long senderId) {
            this.senderId = senderId;
            return this;
        }

        public MessageRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MessageRequest build() {
            return new MessageRequest(roomId, senderId, message);
        }

        public String toString() {
            return "MessageRequest.MessageRequestBuilder(roomId=" + this.roomId + ", senderId=" + this.senderId + ", message=" + this.message + ")";
        }
    }
}
