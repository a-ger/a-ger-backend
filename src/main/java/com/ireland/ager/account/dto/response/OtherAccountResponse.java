package com.ireland.ager.account.dto.response;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.product.entity.Product;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@ToString
@Builder
public class OtherAccountResponse {
    Long accountId;
    String accountEmail;
    String profileNickname;
    String userName;
    String profileImageUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Set<Product> productList = new HashSet<>();

    public static OtherAccountResponse toOtherAccountResponse(Account account) {
        return OtherAccountResponse.builder()
                .profileNickname(account.getProfileNickname())
                .userName(account.getUserName())
                .accountEmail(account.getAccountEmail())
                .profileImageUrl(account.getProfileImageUrl())
                .accountId(account.getAccountId())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .productList(account.getProducts())
                .build();
    }
}
