package com.ireland.ager.product.service;

import com.ireland.ager.account.service.AccountServiceImpl;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.entity.ProductStatus;
import com.ireland.ager.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeServiceImpl {

    private final ProductRepository productRepository;
    private final AccountServiceImpl accountService;

    //TODO : accessToken에 따라 buyer, seller 구분하고, 그에 따른 로직을 다르게 구성해야 한다.
    public Boolean isUpdated(long productId,
                             String accessToken,
                             String status) {

        Product product = productRepository.findById(productId).orElseThrow(null);

        if (product.getAccount().equals(accountService.findAccountByAccessToken(accessToken))) {
            if (status.equals("SALE")) {
                product.setStatusSale();
                return true;
            } else if (status.equals("RESERVATION")) {
                product.setStatusReservation();
                return true;
            } else if (status.equals("COMPLETE")) {
                product.setStatusCompleted();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 현재 상태 체크
    public ProductStatus checkStatus(long productId) {
        // 해당 제품을 조회
        Product product = productRepository.findById(productId).orElseThrow(null);
        // 제품의 상태 확인 후 해당하는 bool 값 리턴
        if (product.getStatus().equals(ProductStatus.SALE)) {
            return product.getStatus();
        } else if (product.getStatus().equals(ProductStatus.RESERVATION)) {
            return product.getStatus();
        } else {
            return product.getStatus();
        }
    }
}
