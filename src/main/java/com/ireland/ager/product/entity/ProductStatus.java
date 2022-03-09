package com.ireland.ager.product.entity;

public enum ProductStatus {
    SALE("판매중"),
    RESERVATION("예약중"),
    COMPLETE("판매완료");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}

