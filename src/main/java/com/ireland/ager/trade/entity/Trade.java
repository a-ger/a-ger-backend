package com.ireland.ager.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Product;

import javax.persistence.*;

/**
 * @Class : Trade
 * @Description : 거래 도메인에 대한 엔티티
 **/
@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "buyer_id")
    private Account buyerId;

    public Trade(Long tradeId, Product product, Account buyerId) {
        this.tradeId = tradeId;
        this.product = product;
        this.buyerId = buyerId;
    }

    public Trade() {
    }

    /**
     * @Method : toTrade
     * @Description : 거래 요청 데이터 객체화
     * @Parameter : [product, buyerId]
     * @Return : Trade
     **/
    public static Trade toTrade(Product product, Account buyerId) {
        return Trade.builder()
                .product(product)
                .buyerId(buyerId)
                .build();
    }

    public static TradeBuilder builder() {
        return new TradeBuilder();
    }

    public Long getTradeId() {
        return this.tradeId;
    }

    public Product getProduct() {
        return this.product;
    }

    public Account getBuyerId() {
        return this.buyerId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    @JsonIgnore
    public void setProduct(Product product) {
        this.product = product;
    }

    @JsonIgnore
    public void setBuyerId(Account buyerId) {
        this.buyerId = buyerId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Trade)) return false;
        final Trade other = (Trade) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$tradeId = this.getTradeId();
        final Object other$tradeId = other.getTradeId();
        if (this$tradeId == null ? other$tradeId != null : !this$tradeId.equals(other$tradeId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Trade;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $tradeId = this.getTradeId();
        result = result * PRIME + ($tradeId == null ? 43 : $tradeId.hashCode());
        return result;
    }

    public static class TradeBuilder {
        private Long tradeId;
        private Product product;
        private Account buyerId;

        TradeBuilder() {
        }

        public TradeBuilder tradeId(Long tradeId) {
            this.tradeId = tradeId;
            return this;
        }

        public TradeBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public TradeBuilder buyerId(Account buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public Trade build() {
            return new Trade(tradeId, product, buyerId);
        }

        public String toString() {
            return "Trade.TradeBuilder(tradeId=" + this.tradeId + ", product=" + this.product + ", buyerId=" + this.buyerId + ")";
        }
    }
}
