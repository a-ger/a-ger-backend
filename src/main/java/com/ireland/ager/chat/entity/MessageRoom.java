package com.ireland.ager.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;
import com.ireland.ager.product.entity.Product;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class : MessageRoom
 * @Description : 메세지룸도메인에 대한 엔티티
 **/
@Entity
@DynamicUpdate
public class MessageRoom extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "seller_id")
    private Account sellerId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "buyer_id")
    private Account buyerId;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus = ReviewStatus.NOTSALE;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus = RoomStatus.FULL;

    public MessageRoom(Long roomId, Product product, Account sellerId, Account buyerId, ReviewStatus reviewStatus, RoomStatus roomStatus) {
        this.roomId = roomId;
        this.product = product;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.reviewStatus = reviewStatus;
        this.roomStatus = roomStatus;
    }

    public MessageRoom() {
    }

    public static MessageRoomBuilder builder() {
        return new MessageRoomBuilder();
    }

    public void toCreateMessageRoom(Product product, Account buyerId) {
        this.setBuyerId(buyerId);
        this.setSellerId(product.getAccount());
        this.setProduct(product);
    }

    public void updateRoomStatus(Account account) {
        if (this.getRoomStatus().equals(RoomStatus.EMPTY)) return;
        if (this.getRoomStatus().equals(RoomStatus.FULL)) {
            this.setRoomStatus((this.getSellerId().equals(account) ? RoomStatus.SELLEROUT : RoomStatus.BUYEROUT));
        } else if (this.getRoomStatus().equals(RoomStatus.SELLEROUT) && this.getBuyerId().equals(account)) {
            this.setRoomStatus(RoomStatus.EMPTY);
        } else if (this.getRoomStatus().equals(RoomStatus.BUYEROUT) && this.getSellerId().equals(account)) { //buyerout
            this.setRoomStatus(RoomStatus.EMPTY);
        }
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public Product getProduct() {
        return this.product;
    }

    public Account getSellerId() {
        return this.sellerId;
    }

    public Account getBuyerId() {
        return this.buyerId;
    }

    public ReviewStatus getReviewStatus() {
        return this.reviewStatus;
    }

    public RoomStatus getRoomStatus() {
        return this.roomStatus;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @JsonIgnore
    public void setProduct(Product product) {
        this.product = product;
    }

    @JsonIgnore
    public void setSellerId(Account sellerId) {
        this.sellerId = sellerId;
    }

    @JsonIgnore
    public void setBuyerId(Account buyerId) {
        this.buyerId = buyerId;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public static class MessageRoomBuilder {
        private Long roomId;
        private Product product;
        private Account sellerId;
        private Account buyerId;
        private ReviewStatus reviewStatus;
        private RoomStatus roomStatus;

        MessageRoomBuilder() {
        }

        public MessageRoomBuilder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public MessageRoomBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public MessageRoomBuilder sellerId(Account sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public MessageRoomBuilder buyerId(Account buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public MessageRoomBuilder reviewStatus(ReviewStatus reviewStatus) {
            this.reviewStatus = reviewStatus;
            return this;
        }

        public MessageRoomBuilder roomStatus(RoomStatus roomStatus) {
            this.roomStatus = roomStatus;
            return this;
        }

        public MessageRoom build() {
            return new MessageRoom(roomId, product, sellerId, buyerId, reviewStatus, roomStatus);
        }

        public String toString() {
            return "MessageRoom.MessageRoomBuilder(roomId=" + this.roomId + ", product=" + this.product + ", sellerId=" + this.sellerId + ", buyerId=" + this.buyerId + ", reviewStatus=" + this.reviewStatus + ", roomStatus=" + this.roomStatus + ")";
        }
    }
}
