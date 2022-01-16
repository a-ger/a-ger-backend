package com.ireland.ager.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "productId", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long productId;

    private String productName;

    private String productPrice;

    private String productDetail;
    private Long productViewCnt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ElementCollection
    @CollectionTable(name = "productUrlList", joinColumns = @JoinColumn(name = "productId")) // 2
    @Column(name = "url") // 3
    private List<String> urlList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "buyer_account_id")
    private Account buyer;

    @ManyToOne
    @JoinColumn(name = "seller_account_id")
    private Account seller;

    public void addAccount(Account updateAccount) {
        updateAccount.getProducts().add(this);
        this.setAccount(updateAccount);
    }

    public void addViewCnt(Product addProduct) {
        this.setProductViewCnt(addProduct.getProductViewCnt() + 1);
    }

}