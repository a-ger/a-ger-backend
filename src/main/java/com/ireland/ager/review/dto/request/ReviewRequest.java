package com.ireland.ager.review.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.review.entity.Review;


/**
 * @Class : ReviewRequest
 * @Description : 리뷰 도메인에 대한 Request DTO
 **/
public class ReviewRequest {
    String comment;
    int stars;

    public ReviewRequest() {
    }

    /**
     * @Method : toReview
     * @Description : 리뷰 정보 데이터 객체화
     * @Parameter : [reviewRequest, seller, product, buyer]
     * @Return : Review
     **/
    public static Review toReview(ReviewRequest reviewRequest, Account seller, Product product, Account buyer) {
        Review review = new Review();
        review.addAccount(seller);
        review.setComment(reviewRequest.comment);
        review.setStars(reviewRequest.stars);
        review.setBuyerNickname(buyer.getProfileNickname());
        review.setBuyerId(buyer.getAccountId());
        review.setSellerNickname(seller.getProfileNickname());
        review.setTitle(product.getProductName());
        return review;
    }

    public String getComment() {
        return this.comment;
    }

    public int getStars() {
        return this.stars;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ReviewRequest)) return false;
        final ReviewRequest other = (ReviewRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$comment = this.getComment();
        final Object other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) return false;
        if (this.getStars() != other.getStars()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReviewRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $comment = this.getComment();
        result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
        result = result * PRIME + this.getStars();
        return result;
    }

    public String toString() {
        return "ReviewRequest(comment=" + this.getComment() + ", stars=" + this.getStars() + ")";
    }
}
