package com.ireland.ager.chat.dto.response;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.chat.entity.Message;
import com.ireland.ager.chat.entity.MessageRoom;

import java.time.LocalDateTime;

/**
 * @Class : MessageSummaryResponse
 * @Description : 메세지도메인에 대한 SummaryResponse DTO
 **/
public class MessageSummaryResponse {
    Long roomId;
    String accountNickname;
    String accountThumbnail;
    String latestMessage;
    LocalDateTime latestAt;

    MessageSummaryResponse(Long roomId, String accountNickname, String accountThumbnail, String latestMessage, LocalDateTime latestAt) {
        this.roomId = roomId;
        this.accountNickname = accountNickname;
        this.accountThumbnail = accountThumbnail;
        this.latestMessage = latestMessage;
        this.latestAt = latestAt;
    }

    /**
     * @Method : toMessageSummaryResponse
     * @Description : 간략한 메세지 데이터 응답객체화
     * @Parameter : [messageRoom, account, message]
     * @Return : MessageSummaryResponse
     **/
    public static MessageSummaryResponse toMessageSummaryResponse(MessageRoom messageRoom
            , Account account, Message message) {
        return MessageSummaryResponse.builder()
                .roomId(messageRoom.getRoomId())
                .accountNickname(account.getProfileNickname())
                .accountThumbnail(account.getProfileImageUrl())
                .latestMessage(message.getMessage())
                .latestAt(message.getCreatedAt())
                .build();
    }

    public static MessageSummaryResponseBuilder builder() {
        return new MessageSummaryResponseBuilder();
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public String getAccountNickname() {
        return this.accountNickname;
    }

    public String getAccountThumbnail() {
        return this.accountThumbnail;
    }

    public String getLatestMessage() {
        return this.latestMessage;
    }

    public LocalDateTime getLatestAt() {
        return this.latestAt;
    }

    public static class MessageSummaryResponseBuilder {
        private Long roomId;
        private String accountNickname;
        private String accountThumbnail;
        private String latestMessage;
        private LocalDateTime latestAt;

        MessageSummaryResponseBuilder() {
        }

        public MessageSummaryResponseBuilder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public MessageSummaryResponseBuilder accountNickname(String accountNickname) {
            this.accountNickname = accountNickname;
            return this;
        }

        public MessageSummaryResponseBuilder accountThumbnail(String accountThumbnail) {
            this.accountThumbnail = accountThumbnail;
            return this;
        }

        public MessageSummaryResponseBuilder latestMessage(String latestMessage) {
            this.latestMessage = latestMessage;
            return this;
        }

        public MessageSummaryResponseBuilder latestAt(LocalDateTime latestAt) {
            this.latestAt = latestAt;
            return this;
        }

        public MessageSummaryResponse build() {
            return new MessageSummaryResponse(roomId, accountNickname, accountThumbnail, latestMessage, latestAt);
        }

        public String toString() {
            return "MessageSummaryResponse.MessageSummaryResponseBuilder(roomId=" + this.roomId + ", accountNickname=" + this.accountNickname + ", accountThumbnail=" + this.accountThumbnail + ", latestMessage=" + this.latestMessage + ", latestAt=" + this.latestAt + ")";
        }
    }
}