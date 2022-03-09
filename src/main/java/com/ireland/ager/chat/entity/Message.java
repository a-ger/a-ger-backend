package com.ireland.ager.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.config.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class : Message
 * @Description : 메세지도메인에 대한 엔티티
 **/
@Entity
public class Message extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private Long senderId;
    private String message;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "room_id")
    private MessageRoom messageRoom;

    public Message(Long messageId, Long senderId, String message, MessageRoom messageRoom) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.message = message;
        this.messageRoom = messageRoom;
    }

    public Message() {
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public Long getMessageId() {
        return this.messageId;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public String getMessage() {
        return this.message;
    }

    public MessageRoom getMessageRoom() {
        return this.messageRoom;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonIgnore
    public void setMessageRoom(MessageRoom messageRoom) {
        this.messageRoom = messageRoom;
    }

    public static class MessageBuilder {
        private Long messageId;
        private Long senderId;
        private String message;
        private MessageRoom messageRoom;

        MessageBuilder() {
        }

        public MessageBuilder messageId(Long messageId) {
            this.messageId = messageId;
            return this;
        }

        public MessageBuilder senderId(Long senderId) {
            this.senderId = senderId;
            return this;
        }

        public MessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MessageBuilder messageRoom(MessageRoom messageRoom) {
            this.messageRoom = messageRoom;
            return this;
        }

        public Message build() {
            return new Message(messageId, senderId, message, messageRoom);
        }

        public String toString() {
            return "Message.MessageBuilder(messageId=" + this.messageId + ", senderId=" + this.senderId + ", message=" + this.message + ", messageRoom=" + this.messageRoom + ")";
        }
    }
}
