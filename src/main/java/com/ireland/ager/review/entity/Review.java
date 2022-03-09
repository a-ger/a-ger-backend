package com.ireland.ager.review.entity;


import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

/**
* @Class : Review
* @Description : 리뷰 도메인에 대한 엔티티
**/
@Entity
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String comment;
    private String title;
    private String buyerNickname;
    private Long buyerId;
    private String sellerNickname;
    private int stars;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Account sellerId;

    public Review(Long reviewId, String comment, String title, String buyerNickname, Long buyerId, String sellerNickname, int stars, Account sellerId) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.title = title;
        this.buyerNickname = buyerNickname;
        this.buyerId = buyerId;
        this.sellerNickname = sellerNickname;
        this.stars = stars;
        this.sellerId = sellerId;
    }

    public Review() {
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    /**
    * @Method : addAccount
    * @Description : 계정 추가
    * @Parameter : [account]
    * @Return : null
    **/
    public void addAccount(Account account) {
        this.sellerId = account;
    }

    public Long getReviewId() {
        return this.reviewId;
    }

    public String getComment() {
        return this.comment;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBuyerNickname() {
        return this.buyerNickname;
    }

    public Long getBuyerId() {
        return this.buyerId;
    }

    public String getSellerNickname() {
        return this.sellerNickname;
    }

    public int getStars() {
        return this.stars;
    }

    public Account getSellerId() {
        return this.sellerId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBuyerNickname(String buyerNickname) {
        this.buyerNickname = buyerNickname;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setSellerNickname(String sellerNickname) {
        this.sellerNickname = sellerNickname;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setSellerId(Account sellerId) {
        this.sellerId = sellerId;
    }

    public static class ReviewBuilder {
        private Long reviewId;
        private String comment;
        private String title;
        private String buyerNickname;
        private Long buyerId;
        private String sellerNickname;
        private int stars;
        private Account sellerId;

        ReviewBuilder() {
        }

        public ReviewBuilder reviewId(Long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public ReviewBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ReviewBuilder buyerNickname(String buyerNickname) {
            this.buyerNickname = buyerNickname;
            return this;
        }

        public ReviewBuilder buyerId(Long buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public ReviewBuilder sellerNickname(String sellerNickname) {
            this.sellerNickname = sellerNickname;
            return this;
        }

        public ReviewBuilder stars(int stars) {
            this.stars = stars;
            return this;
        }

        public ReviewBuilder sellerId(Account sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Review build() {
            return new Review(reviewId, comment, title, buyerNickname, buyerId, sellerNickname, stars, sellerId);
        }

        public String toString() {
            return "Review.ReviewBuilder(reviewId=" + this.reviewId + ", comment=" + this.comment + ", title=" + this.title + ", buyerNickname=" + this.buyerNickname + ", buyerId=" + this.buyerId + ", sellerNickname=" + this.sellerNickname + ", stars=" + this.stars + ", sellerId=" + this.sellerId + ")";
        }
    }
}
