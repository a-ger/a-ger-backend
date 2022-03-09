package com.ireland.ager.account.entity;

import com.ireland.ager.board.entity.Board;
import com.ireland.ager.board.entity.Comment;
import com.ireland.ager.config.BaseEntity;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.review.entity.Review;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @Class : Account
 * @Description : 계정 도메인에 대한 엔티티
 **/
@Entity
@DynamicUpdate
public class Account extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;
    String accountEmail;
    String profileNickname;
    String userName;
    String profileImageUrl;
    String accessToken;
    @Formula("(select avg(r.stars) from review r where r.seller_id=account_id)")
    Double avgStar;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account")
    Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "sellerId", cascade = CascadeType.ALL)
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Account(Long accountId, String accountEmail, String profileNickname, String userName, String profileImageUrl, String accessToken, Double avgStar, Set<Product> products, Set<Review> reviews, List<Board> boards, List<Comment> comments) {
        this.accountId = accountId;
        this.accountEmail = accountEmail;
        this.profileNickname = profileNickname;
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.accessToken = accessToken;
        this.avgStar = avgStar;
        this.products = products;
        this.reviews = reviews;
        this.boards = boards;
        this.comments = comments;
    }

    public Account() {
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public String getAccountEmail() {
        return this.accountEmail;
    }

    public String getProfileNickname() {
        return this.profileNickname;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public Double getAvgStar() {
        return this.avgStar;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public Set<Review> getReviews() {
        return this.reviews;
    }

    public List<Board> getBoards() {
        return this.boards;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public void setProfileNickname(String profileNickname) {
        this.profileNickname = profileNickname;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAvgStar(Double avgStar) {
        this.avgStar = avgStar;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Account)) return false;
        final Account other = (Account) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$accountId = this.getAccountId();
        final Object other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Account;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $accountId = this.getAccountId();
        result = result * PRIME + ($accountId == null ? 43 : $accountId.hashCode());
        return result;
    }
}