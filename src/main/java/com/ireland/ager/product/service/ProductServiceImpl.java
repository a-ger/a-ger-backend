package com.ireland.ager.product.service;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.service.AccountServiceImpl;
import com.ireland.ager.config.exception.InvaildUploadException;
import com.ireland.ager.config.exception.NotFoundException;
import com.ireland.ager.config.exception.UnAuthorizedTokenException;
import com.ireland.ager.product.dto.request.ProductRequest;
import com.ireland.ager.product.dto.request.ProductUpdateRequest;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;
    private final AccountServiceImpl accountService;
    private final UploadServiceImpl uploadService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(String accessToken,
                                 ProductRequest productRequest,
                                 List<MultipartFile> multipartFile) {
        Account account = accountService.findAccountByAccessToken(accessToken);
        List<String> uploadImagesUrl = uploadService.uploadImages(multipartFile);
        Product product = productRequest.toProduct(account, uploadImagesUrl);
        productRepository.save(product);
        return product;
    }

    //FIXME 캐시 적용 하는 곳,,
//    @Cacheable(key = "#productId", value = "product", cacheManager = "redisCacheManager")
    public Product findProductById(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow(NotFoundException::new);
        product.addViewCnt(product);
        productRepository.save(product);
        return product;
    }

    public Product updateProductById(Long productId,
                                     String accessToken,
                                     List<MultipartFile> multipartFile,
                                     ProductUpdateRequest productUpdateRequest) {
        // 원래 정보를 꺼내온다.
        Product productById = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        if (!(productById.getAccount().getAccountId().equals(accountService.findAccountByAccessToken(accessToken).getAccountId()))) {
            // 수정하고자 하는 사람과 현재 토큰 주인이 다르면 False
            throw new UnAuthorizedTokenException();
        }
        validateFileExists(multipartFile);
        //들어온 multiFile의 리스트를 확인 하는 과정
        List<String> updateFileImageUrlList = null;
        List<String> currentFileImageUrlList = productById.getUrlList();
        uploadService.delete(currentFileImageUrlList);
        try {
            updateFileImageUrlList = uploadService.uploadImages(multipartFile);
            productById.setUrlList(updateFileImageUrlList);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        //REMARK 악취가 난다..... 해결 완료
        Account accountById = accountService.findAccountById(productById.getAccount().getAccountId());
        Product toProductUpdate = productUpdateRequest.toProductUpdate(productById, accountById, productById.getUrlList());
        productRepository.save(toProductUpdate);
        return toProductUpdate;
    }

    //FIXME 상품 삭제시 S3저장소에 이미지도 삭제하기 해결 완료
    public void deleteProductById(Long productId,String accessToken) {
        Optional<Product> productById = productRepository.findById(productId);
        if (!(productById.orElseThrow(NotFoundException::new).getAccount().getAccountId().equals(accountService.findAccountByAccessToken(accessToken).getAccountId()))) {
            // 수정하고자 하는 사람과 현재 토큰 주인이 다르면 False
            throw new UnAuthorizedTokenException();
        }
        uploadService.delete(productRepository.findById(productId).orElseThrow(NotFoundException::new).getUrlList());
        productRepository.deleteById(productId);
    }
    //Todo 들어온 파일리스트가 널값이면 사진갯수 에러를 반환하는 메서드이다. 하지만 파일의 갯수가 없어도 사이즈가 1로 찍힌다.
    // 파일 사이즈는 콘솔창에 업로드 파일의 갯수 찾아서 보면 확인 가능
    public void validateFileExists(List<MultipartFile> file){
        if(file.isEmpty())
            throw new InvaildUploadException();
    }
}