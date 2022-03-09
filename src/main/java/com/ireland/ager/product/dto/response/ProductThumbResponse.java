package com.ireland.ager.product.dto.response;

import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class : ProductThumbResponse
 * @Description : 상품도메인에 대한 ThumbResponse DTO
 **/
public class ProductThumbResponse {
    String productName;
    String productPrice;
    String productStatus;
    Long productViewCnt;
    Category category;
    LocalDateTime createdAt;
    String thumbNailUrl;

    ProductThumbResponse(String productName, String productPrice, String productStatus, Long productViewCnt, Category category, LocalDateTime createdAt, String thumbNailUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productViewCnt = productViewCnt;
        this.category = category;
        this.createdAt = createdAt;
        this.thumbNailUrl = thumbNailUrl;
    }

    /**
     * @Method : toProductThumbResponse
     * @Description : 간략한 상품정보 데이터 응답객체화
     * @Parameter : [product]
     * @Return : ProductThumbResponse
     **/
    public static ProductThumbResponse toProductThumbResponse(Product product) {
        return ProductThumbResponse.builder()
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productStatus(product.getStatus().name())
                .productViewCnt(product.getProductViewCnt())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .thumbNailUrl(product.getThumbNailUrl())
                .build();
    }

    /**
     * @Method : toProductListResponse
     * @Description : 간략한 상품정보 리스트 데이터 응답객체화
     * @Parameter : [productList]
     * @Return : List<ProductThumbResponse>
     **/
    public static List<ProductThumbResponse> toProductListResponse(List<Product> productList) {
        List<ProductThumbResponse> productResponseList = new ArrayList<>();
        for (Product product : productList) {
            ProductThumbResponse productResponse = ProductThumbResponse.toProductThumbResponse(product);
            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    public static ProductThumbResponseBuilder builder() {
        return new ProductThumbResponseBuilder();
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductPrice() {
        return this.productPrice;
    }

    public String getProductStatus() {
        return this.productStatus;
    }

    public Long getProductViewCnt() {
        return this.productViewCnt;
    }

    public Category getCategory() {
        return this.category;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public String getThumbNailUrl() {
        return this.thumbNailUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public void setProductViewCnt(Long productViewCnt) {
        this.productViewCnt = productViewCnt;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductThumbResponse)) return false;
        final ProductThumbResponse other = (ProductThumbResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$productName = this.getProductName();
        final Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName))
            return false;
        final Object this$productPrice = this.getProductPrice();
        final Object other$productPrice = other.getProductPrice();
        if (this$productPrice == null ? other$productPrice != null : !this$productPrice.equals(other$productPrice))
            return false;
        final Object this$productStatus = this.getProductStatus();
        final Object other$productStatus = other.getProductStatus();
        if (this$productStatus == null ? other$productStatus != null : !this$productStatus.equals(other$productStatus))
            return false;
        final Object this$productViewCnt = this.getProductViewCnt();
        final Object other$productViewCnt = other.getProductViewCnt();
        if (this$productViewCnt == null ? other$productViewCnt != null : !this$productViewCnt.equals(other$productViewCnt))
            return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$thumbNailUrl = this.getThumbNailUrl();
        final Object other$thumbNailUrl = other.getThumbNailUrl();
        if (this$thumbNailUrl == null ? other$thumbNailUrl != null : !this$thumbNailUrl.equals(other$thumbNailUrl))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductThumbResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        final Object $productPrice = this.getProductPrice();
        result = result * PRIME + ($productPrice == null ? 43 : $productPrice.hashCode());
        final Object $productStatus = this.getProductStatus();
        result = result * PRIME + ($productStatus == null ? 43 : $productStatus.hashCode());
        final Object $productViewCnt = this.getProductViewCnt();
        result = result * PRIME + ($productViewCnt == null ? 43 : $productViewCnt.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $thumbNailUrl = this.getThumbNailUrl();
        result = result * PRIME + ($thumbNailUrl == null ? 43 : $thumbNailUrl.hashCode());
        return result;
    }

    public String toString() {
        return "ProductThumbResponse(productName=" + this.getProductName() + ", productPrice=" + this.getProductPrice() + ", productStatus=" + this.getProductStatus() + ", productViewCnt=" + this.getProductViewCnt() + ", category=" + this.getCategory() + ", createdAt=" + this.getCreatedAt() + ", thumbNailUrl=" + this.getThumbNailUrl() + ")";
    }

    public static class ProductThumbResponseBuilder {
        private String productName;
        private String productPrice;
        private String productStatus;
        private Long productViewCnt;
        private Category category;
        private LocalDateTime createdAt;
        private String thumbNailUrl;

        ProductThumbResponseBuilder() {
        }

        public ProductThumbResponseBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductThumbResponseBuilder productPrice(String productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public ProductThumbResponseBuilder productStatus(String productStatus) {
            this.productStatus = productStatus;
            return this;
        }

        public ProductThumbResponseBuilder productViewCnt(Long productViewCnt) {
            this.productViewCnt = productViewCnt;
            return this;
        }

        public ProductThumbResponseBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductThumbResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductThumbResponseBuilder thumbNailUrl(String thumbNailUrl) {
            this.thumbNailUrl = thumbNailUrl;
            return this;
        }

        public ProductThumbResponse build() {
            return new ProductThumbResponse(productName, productPrice, productStatus, productViewCnt, category, createdAt, thumbNailUrl);
        }

        public String toString() {
            return "ProductThumbResponse.ProductThumbResponseBuilder(productName=" + this.productName + ", productPrice=" + this.productPrice + ", productStatus=" + this.productStatus + ", productViewCnt=" + this.productViewCnt + ", category=" + this.category + ", createdAt=" + this.createdAt + ", thumbNailUrl=" + this.thumbNailUrl + ")";
        }
    }
}
