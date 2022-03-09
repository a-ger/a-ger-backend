package com.ireland.ager.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class : Url
 * @Description : URL도메인에 대한 엔티티
 **/
@Entity
@DynamicUpdate
public class Url implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long urlId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product productId;

    private String url;

    public Url(Long urlId, Product productId, String url) {
        this.urlId = urlId;
        this.productId = productId;
        this.url = url;
    }

    public Url() {
    }

    public static UrlBuilder builder() {
        return new UrlBuilder();
    }

    public Long getUrlId() {
        return this.urlId;
    }

    public Product getProductId() {
        return this.productId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    @JsonIgnore
    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Url)) return false;
        final Url other = (Url) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$urlId = this.getUrlId();
        final Object other$urlId = other.getUrlId();
        if (this$urlId == null ? other$urlId != null : !this$urlId.equals(other$urlId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Url;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $urlId = this.getUrlId();
        result = result * PRIME + ($urlId == null ? 43 : $urlId.hashCode());
        return result;
    }

    public static class UrlBuilder {
        private Long urlId;
        private Product productId;
        private String url;

        UrlBuilder() {
        }

        public UrlBuilder urlId(Long urlId) {
            this.urlId = urlId;
            return this;
        }

        public UrlBuilder productId(Product productId) {
            this.productId = productId;
            return this;
        }

        public UrlBuilder url(String url) {
            this.url = url;
            return this;
        }

        public Url build() {
            return new Url(urlId, productId, url);
        }

        public String toString() {
            return "Url.UrlBuilder(urlId=" + this.urlId + ", productId=" + this.productId + ", url=" + this.url + ")";
        }
    }
}
