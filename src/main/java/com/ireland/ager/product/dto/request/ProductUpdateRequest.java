package com.ireland.ager.product.dto.request;

import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.entity.ProductStatus;
import com.ireland.ager.product.entity.Url;
import org.slf4j.Logger;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @Class : ProductUpdateRequest
 * @Description : 상품도메인에 대한 UpdateRequest DTO
 **/
public class ProductUpdateRequest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProductUpdateRequest.class);
    @NotBlank(message = "3010")
    String productName;
    @NotBlank(message = "3020")
    @Min(value = 0, message = "3021")
    String productPrice;
    @NotBlank(message = "3030")
    String productDetail;
    @NotBlank(message = "3040")
    String category;
    @NotBlank(message = "3050")
    String status;

    public ProductUpdateRequest() {
    }

    /**
     * @Method : toProductUpdate
     * @Description : 상품수정정보 데이터 객체화
     * @Parameter : [product, uploadImageUrl]
     * @Return : Product
     **/
    public Product toProductUpdate(Product product,
                                   List<String> uploadImageUrl) {
        for (String str : uploadImageUrl) {
            Url url = new Url();
            url.setUrl(str);
            product.addUrl(url);
        }
        product.setProductDetail(this.productDetail);
        product.setProductPrice(productPrice);
        product.setProductViewCnt(product.getProductViewCnt());
        product.setProductName(productName);
        product.setStatus(ProductStatus.valueOf(this.status));
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

    public @NotBlank(message = "3050") String getStatus() {
        return this.status;
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

    public void setStatus(@NotBlank(message = "3050") String status) {
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductUpdateRequest)) return false;
        final ProductUpdateRequest other = (ProductUpdateRequest) o;
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
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductUpdateRequest;
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
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "ProductUpdateRequest(productName=" + this.getProductName() + ", productPrice=" + this.getProductPrice() + ", productDetail=" + this.getProductDetail() + ", category=" + this.getCategory() + ", status=" + this.getStatus() + ")";
    }
}