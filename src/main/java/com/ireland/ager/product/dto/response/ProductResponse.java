package com.ireland.ager.product.dto.response;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.entity.ProductStatus;
import com.ireland.ager.product.entity.Url;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Class : ProductResponse
 * @Description : 상품도메인에 대한 Response DTO
 **/
public class ProductResponse {
    Long productId;
    String productName;
    String productPrice;
    String productDetail;
    Long productViewCnt;
    Category category;
    ProductStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String thumbNailUrl;
    boolean isOwner = true;
    List<Url> urlList;

    ProductResponse(Long productId, String productName, String productPrice, String productDetail, Long productViewCnt, Category category, ProductStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String thumbNailUrl, boolean isOwner, List<Url> urlList) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDetail = productDetail;
        this.productViewCnt = productViewCnt;
        this.category = category;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.thumbNailUrl = thumbNailUrl;
        this.isOwner = isOwner;
        this.urlList = urlList;
    }

    /**
     * @Method : toProductResponse
     * @Description : 상품정보 데이터 응답객체화
     * @Parameter : [product, account]
     * @Return : ProductResponse
     **/
    public static ProductResponse toProductResponse(Product product, Account account) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDetail(product.getProductDetail())
                .productViewCnt(product.getProductViewCnt())
                .category(product.getCategory())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .thumbNailUrl(product.getThumbNailUrl())
                .isOwner(product.getAccount().equals(account))
                .urlList(product.getUrlList())
                .build();
    }

    public static ProductResponseBuilder builder() {
        return new ProductResponseBuilder();
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public String getThumbNailUrl() {
        return this.thumbNailUrl;
    }

    public boolean isOwner() {
        return this.isOwner;
    }

    public List<Url> getUrlList() {
        return this.urlList;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public void setUrlList(List<Url> urlList) {
        this.urlList = urlList;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductResponse)) return false;
        final ProductResponse other = (ProductResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$productName = this.getProductName();
        final Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName))
            return false;
        final Object this$productPrice = this.getProductPrice();
        final Object other$productPrice = other.getProductPrice();
        if (this$productPrice == null ? other$productPrice != null : !this$productPrice.equals(other$productPrice))
            return false;
        final Object this$productDetail = this.getProductDetail();
        final Object other$productDetail = other.getProductDetail();
        if (this$productDetail == null ? other$productDetail != null : !this$productDetail.equals(other$productDetail))
            return false;
        final Object this$productViewCnt = this.getProductViewCnt();
        final Object other$productViewCnt = other.getProductViewCnt();
        if (this$productViewCnt == null ? other$productViewCnt != null : !this$productViewCnt.equals(other$productViewCnt))
            return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        final Object this$thumbNailUrl = this.getThumbNailUrl();
        final Object other$thumbNailUrl = other.getThumbNailUrl();
        if (this$thumbNailUrl == null ? other$thumbNailUrl != null : !this$thumbNailUrl.equals(other$thumbNailUrl))
            return false;
        if (this.isOwner() != other.isOwner()) return false;
        final Object this$urlList = this.getUrlList();
        final Object other$urlList = other.getUrlList();
        if (this$urlList == null ? other$urlList != null : !this$urlList.equals(other$urlList)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        final Object $productPrice = this.getProductPrice();
        result = result * PRIME + ($productPrice == null ? 43 : $productPrice.hashCode());
        final Object $productDetail = this.getProductDetail();
        result = result * PRIME + ($productDetail == null ? 43 : $productDetail.hashCode());
        final Object $productViewCnt = this.getProductViewCnt();
        result = result * PRIME + ($productViewCnt == null ? 43 : $productViewCnt.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        final Object $thumbNailUrl = this.getThumbNailUrl();
        result = result * PRIME + ($thumbNailUrl == null ? 43 : $thumbNailUrl.hashCode());
        result = result * PRIME + (this.isOwner() ? 79 : 97);
        final Object $urlList = this.getUrlList();
        result = result * PRIME + ($urlList == null ? 43 : $urlList.hashCode());
        return result;
    }

    public String toString() {
        return "ProductResponse(productId=" + this.getProductId() + ", productName=" + this.getProductName() + ", productPrice=" + this.getProductPrice() + ", productDetail=" + this.getProductDetail() + ", productViewCnt=" + this.getProductViewCnt() + ", category=" + this.getCategory() + ", status=" + this.getStatus() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ", thumbNailUrl=" + this.getThumbNailUrl() + ", isOwner=" + this.isOwner() + ", urlList=" + this.getUrlList() + ")";
    }

    public static class ProductResponseBuilder {
        private Long productId;
        private String productName;
        private String productPrice;
        private String productDetail;
        private Long productViewCnt;
        private Category category;
        private ProductStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String thumbNailUrl;
        private boolean isOwner;
        private List<Url> urlList;

        ProductResponseBuilder() {
        }

        public ProductResponseBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public ProductResponseBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductResponseBuilder productPrice(String productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public ProductResponseBuilder productDetail(String productDetail) {
            this.productDetail = productDetail;
            return this;
        }

        public ProductResponseBuilder productViewCnt(Long productViewCnt) {
            this.productViewCnt = productViewCnt;
            return this;
        }

        public ProductResponseBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductResponseBuilder status(ProductStatus status) {
            this.status = status;
            return this;
        }

        public ProductResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductResponseBuilder thumbNailUrl(String thumbNailUrl) {
            this.thumbNailUrl = thumbNailUrl;
            return this;
        }

        public ProductResponseBuilder isOwner(boolean isOwner) {
            this.isOwner = isOwner;
            return this;
        }

        public ProductResponseBuilder urlList(List<Url> urlList) {
            this.urlList = urlList;
            return this;
        }

        public ProductResponse build() {
            return new ProductResponse(productId, productName, productPrice, productDetail, productViewCnt, category, status, createdAt, updatedAt, thumbNailUrl, isOwner, urlList);
        }

        public String toString() {
            return "ProductResponse.ProductResponseBuilder(productId=" + this.productId + ", productName=" + this.productName + ", productPrice=" + this.productPrice + ", productDetail=" + this.productDetail + ", productViewCnt=" + this.productViewCnt + ", category=" + this.category + ", status=" + this.status + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", thumbNailUrl=" + this.thumbNailUrl + ", isOwner=" + this.isOwner + ", urlList=" + this.urlList + ")";
        }
    }
}