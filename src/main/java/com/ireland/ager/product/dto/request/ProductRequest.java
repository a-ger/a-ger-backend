package com.ireland.ager.product.dto.request;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.entity.ProductStatus;
import com.ireland.ager.product.entity.Url;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Class : ProductRequest
 * @Description : 상품도메인에 대한 Request DTO
 **/
public class ProductRequest {
    @NotBlank(message = "3010")
    String productName;
    @NotBlank(message = "3020")
    @Min(value = 0, message = "3021")
    String productPrice;
    @NotBlank(message = "3030")
    String productDetail;
    @NotBlank(message = "3040")
    String category;

    public ProductRequest() {
    }

    /**
     * @Method : toProduct
     * @Description : 상품정보 데이터 객체화
     * @Parameter : [account, uploadImageUrl, thumbNailUrl]
     * @Return : Product
     **/
    public Product toProduct(Account account,
                             List<String> uploadImageUrl, String thumbNailUrl) {
        Product product = new Product();
        for (String str : uploadImageUrl) {
            Url url = new Url();
            url.setUrl(str);
            product.addUrl(url);
        }
        product.addAccount(account);
        product.setProductDetail(this.productDetail);
        product.setProductPrice(productPrice);
        product.setProductViewCnt(0L);
        product.setProductName(productName);
        product.setStatus(ProductStatus.SALE);
        product.setThumbNailUrl(thumbNailUrl);
        product.setCategory(Category.valueOf(this.category));
        return product;
    }

    public @NotBlank(message = "3010") String getProductName() {
        return this.productName;
    }

    public @NotBlank(message = "3020") @Min(value = 0, message = "3021") String getProductPrice() {
        return this.productPrice;
    }

    public @NotBlank(message = "3030") String getProductDetail() {
        return this.productDetail;
    }

    public @NotBlank(message = "3040") String getCategory() {
        return this.category;
    }

    public void setProductName(@NotBlank(message = "3010") String productName) {
        this.productName = productName;
    }

    public void setProductPrice(@NotBlank(message = "3020") @Min(value = 0, message = "3021") String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDetail(@NotBlank(message = "3030") String productDetail) {
        this.productDetail = productDetail;
    }

    public void setCategory(@NotBlank(message = "3040") String category) {
        this.category = category;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductRequest)) return false;
        final ProductRequest other = (ProductRequest) o;
        if (!other.canEqual((Object) this)) return false;
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
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        final Object $productPrice = this.getProductPrice();
        result = result * PRIME + ($productPrice == null ? 43 : $productPrice.hashCode());
        final Object $productDetail = this.getProductDetail();
        result = result * PRIME + ($productDetail == null ? 43 : $productDetail.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        return result;
    }

    public String toString() {
        return "ProductRequest(productName=" + this.getProductName() + ", productPrice=" + this.getProductPrice() + ", productDetail=" + this.getProductDetail() + ", category=" + this.getCategory() + ")";
    }
}