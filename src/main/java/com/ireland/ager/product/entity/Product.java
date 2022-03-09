package com.ireland.ager.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Class : Product
 * @Description : 상품도메인에 대한 엔티티
 **/
@Entity
@DynamicUpdate
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private String productPrice;

    private String productDetail;
    private Long productViewCnt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private String thumbNailUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId", orphanRemoval = true)
    private List<Url> urlList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Product(Long productId, String productName, String productPrice, String productDetail, Long productViewCnt, Category category, ProductStatus status, String thumbNailUrl, List<Url> urlList, Account account) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDetail = productDetail;
        this.productViewCnt = productViewCnt;
        this.category = category;
        this.status = status;
        this.thumbNailUrl = thumbNailUrl;
        this.urlList = urlList;
        this.account = account;
    }

    public Product() {
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    /**
     * @Method : addAccount
     * @Description : 계정 엔티티와 양방향 맵핑
     * @Parameter : [updateAccount]
     * @Return : null
     **/
    public void addAccount(Account updateAccount) {
        this.setAccount(updateAccount);
    }

    /**
     * @Method : addUrl
     * @Description : Url 엔티티와 양방량 맵핑
     * @Parameter : [url]
     * @Return : null
     **/
    public void addUrl(Url url) {
        this.getUrlList().add(url);
        url.setProductId(this);
    }

    /**
     * @Method : deleteUrl
     * @Description : Url 엔티티 삭제
     * @Parameter : []
     * @Return : null
     **/
    public void deleteUrl() {
        for (Iterator<Url> it = this.getUrlList().iterator(); it.hasNext(); ) {
            Url url = it.next();
            url.setProductId(null);
            it.remove();
        }
    }

    public Long getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductPrice() {
        return this.productPrice;
    }

    public String getProductDetail() {
        return this.productDetail;
    }

    public Long getProductViewCnt() {
        return this.productViewCnt;
    }

    public Category getCategory() {
        return this.category;
    }

    public ProductStatus getStatus() {
        return this.status;
    }

    public String getThumbNailUrl() {
        return this.thumbNailUrl;
    }

    public List<Url> getUrlList() {
        return this.urlList;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public void setProductViewCnt(Long productViewCnt) {
        this.productViewCnt = productViewCnt;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public void setUrlList(List<Url> urlList) {
        this.urlList = urlList;
    }

    @JsonIgnore
    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        return result;
    }

    public static class ProductBuilder {
        private Long productId;
        private String productName;
        private String productPrice;
        private String productDetail;
        private Long productViewCnt;
        private Category category;
        private ProductStatus status;
        private String thumbNailUrl;
        private List<Url> urlList;
        private Account account;

        ProductBuilder() {
        }

        public ProductBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductBuilder productPrice(String productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public ProductBuilder productDetail(String productDetail) {
            this.productDetail = productDetail;
            return this;
        }

        public ProductBuilder productViewCnt(Long productViewCnt) {
            this.productViewCnt = productViewCnt;
            return this;
        }

        public ProductBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder status(ProductStatus status) {
            this.status = status;
            return this;
        }

        public ProductBuilder thumbNailUrl(String thumbNailUrl) {
            this.thumbNailUrl = thumbNailUrl;
            return this;
        }

        public ProductBuilder urlList(List<Url> urlList) {
            this.urlList = urlList;
            return this;
        }

        public ProductBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public Product build() {
            return new Product(productId, productName, productPrice, productDetail, productViewCnt, category, status, thumbNailUrl, urlList, account);
        }

        public String toString() {
            return "Product.ProductBuilder(productId=" + this.productId + ", productName=" + this.productName + ", productPrice=" + this.productPrice + ", productDetail=" + this.productDetail + ", productViewCnt=" + this.productViewCnt + ", category=" + this.category + ", status=" + this.status + ", thumbNailUrl=" + this.thumbNailUrl + ", urlList=" + this.urlList + ", account=" + this.account + ")";
        }
    }
}