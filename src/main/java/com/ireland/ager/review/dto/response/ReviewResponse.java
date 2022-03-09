package com.ireland.ager.review.dto.response;


import com.ireland.ager.review.entity.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
* @Class : ReviewResponse
* @Description : 리뷰 도메인에 대한 Response DTO
**/
public class ReviewResponse {
    Long buyerId;
    String buyerName;
    String comment;
    Long sellerId;
    String sellerNickname;
    int stars;
    LocalDateTime createdAt;

    ReviewResponse(Long buyerId, String buyerName, String comment, Long sellerId, String sellerNickname, int stars, LocalDateTime createdAt) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.comment = comment;
        this.sellerId = sellerId;
        this.sellerNickname = sellerNickname;
        this.stars = stars;
        this.createdAt = createdAt;
    }

    /**
     * @Method : toReviewResponse
     * @Description : 리뷰 데이터 응답 객체화
     * @Parameter : [review]
     * @Return : ReviewResponse
     **/
    public static ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .buyerName(review.getBuyerNickname())
                .buyerId(review.getBuyerId())
                .comment(review.getComment())
                .sellerNickname(review.getSellerNickname())
                .sellerId(review.getSellerId().getAccountId())
                .stars(review.getStars())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static List<ReviewResponse> toReviewResponse(List<Review> reviewList) {
        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewResponse reviewResponse = ReviewResponse.toReviewResponse(review);
            reviewResponseList.add(reviewResponse);
        }
        return reviewResponseList;
    }

    public static ReviewResponseBuilder builder() {
        return new ReviewResponseBuilder();
    }

    public Long getBuyerId() {
        return this.buyerId;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public String getComment() {
        return this.comment;
    }

    public Long getSellerId() {
        return this.sellerId;
    }

    public String getSellerNickname() {
        return this.sellerNickname;
    }

    public int getStars() {
        return this.stars;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setSellerNickname(String sellerNickname) {
        this.sellerNickname = sellerNickname;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ReviewResponse)) return false;
        final ReviewResponse other = (ReviewResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$buyerId = this.getBuyerId();
        final Object other$buyerId = other.getBuyerId();
        if (this$buyerId == null ? other$buyerId != null : !this$buyerId.equals(other$buyerId)) return false;
        final Object this$buyerName = this.getBuyerName();
        final Object other$buyerName = other.getBuyerName();
        if (this$buyerName == null ? other$buyerName != null : !this$buyerName.equals(other$buyerName)) return false;
        final Object this$comment = this.getComment();
        final Object other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) return false;
        final Object this$sellerId = this.getSellerId();
        final Object other$sellerId = other.getSellerId();
        if (this$sellerId == null ? other$sellerId != null : !this$sellerId.equals(other$sellerId)) return false;
        final Object this$sellerNickname = this.getSellerNickname();
        final Object other$sellerNickname = other.getSellerNickname();
        if (this$sellerNickname == null ? other$sellerNickname != null : !this$sellerNickname.equals(other$sellerNickname))
            return false;
        if (this.getStars() != other.getStars()) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReviewResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $buyerId = this.getBuyerId();
        result = result * PRIME + ($buyerId == null ? 43 : $buyerId.hashCode());
        final Object $buyerName = this.getBuyerName();
        result = result * PRIME + ($buyerName == null ? 43 : $buyerName.hashCode());
        final Object $comment = this.getComment();
        result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
        final Object $sellerId = this.getSellerId();
        result = result * PRIME + ($sellerId == null ? 43 : $sellerId.hashCode());
        final Object $sellerNickname = this.getSellerNickname();
        result = result * PRIME + ($sellerNickname == null ? 43 : $sellerNickname.hashCode());
        result = result * PRIME + this.getStars();
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "ReviewResponse(buyerId=" + this.getBuyerId() + ", buyerName=" + this.getBuyerName() + ", comment=" + this.getComment() + ", sellerId=" + this.getSellerId() + ", sellerNickname=" + this.getSellerNickname() + ", stars=" + this.getStars() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class ReviewResponseBuilder {
        private Long buyerId;
        private String buyerName;
        private String comment;
        private Long sellerId;
        private String sellerNickname;
        private int stars;
        private LocalDateTime createdAt;

        ReviewResponseBuilder() {
        }

        public ReviewResponseBuilder buyerId(Long buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public ReviewResponseBuilder buyerName(String buyerName) {
            this.buyerName = buyerName;
            return this;
        }

        public ReviewResponseBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewResponseBuilder sellerId(Long sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public ReviewResponseBuilder sellerNickname(String sellerNickname) {
            this.sellerNickname = sellerNickname;
            return this;
        }

        public ReviewResponseBuilder stars(int stars) {
            this.stars = stars;
            return this;
        }

        public ReviewResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewResponse build() {
            return new ReviewResponse(buyerId, buyerName, comment, sellerId, sellerNickname, stars, createdAt);
        }

        public String toString() {
            return "ReviewResponse.ReviewResponseBuilder(buyerId=" + this.buyerId + ", buyerName=" + this.buyerName + ", comment=" + this.comment + ", sellerId=" + this.sellerId + ", sellerNickname=" + this.sellerNickname + ", stars=" + this.stars + ", createdAt=" + this.createdAt + ")";
        }
    }
}
