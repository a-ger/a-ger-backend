package com.ireland.ager.product.controller;


import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.service.AccountServiceImpl;
import com.ireland.ager.account.service.AuthServiceImpl;
import com.ireland.ager.main.common.CommonResult;
import com.ireland.ager.main.common.SingleResult;
import com.ireland.ager.main.common.service.ResponseService;
import com.ireland.ager.main.service.UploadServiceImpl;
import com.ireland.ager.product.dto.request.ProductRequest;
import com.ireland.ager.product.dto.request.ProductUpdateRequest;
import com.ireland.ager.product.dto.response.ProductResponse;
import com.ireland.ager.product.service.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @Class : ProductController
 * @Description : 상품도메인에 대한 컨트롤러
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = {"*"}, maxAge = 6000)
@RequestMapping("/api/product")
public class ProductController {

    private final ProductServiceImpl productService;
    private final AccountServiceImpl accountService;
    private final ResponseService responseService;
    private final UploadServiceImpl uploadService;

    /**
     * @Method : findProductById
     * @Description : 상품아이디로 상품 조회
     * @Parameter : [accessToken, productId]
     * @Return : ResponseEntity<SingleResult<ProductResponse>>
     **/
    @GetMapping("/{productId}")
    @ApiOperation(value = "상품 아이디로 상품 조회")
    public ResponseEntity<SingleResult<ProductResponse>> findProductById(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "제품 아이디", required = true)
            @PathVariable Long productId) {
        String[] splitToken = accessToken.split(" ");
        productService.addViewCntToRedis(productId);
        Account accountByAccessToken = accountService.findAccountByAccessToken(splitToken[1]);
        ProductResponse productResponse = ProductResponse.toProductResponse(
                productService.findProductById(productId)
                , accountByAccessToken);
        return new ResponseEntity<>(responseService.getSingleResult(productResponse), HttpStatus.OK);
    }

    /**
     * @Method : createProduct
     * @Description : 상품정보 생성
     * @Parameter : [accessToken, multipartFile, productRequest, bindingResult]
     * @Return : ResponseEntity<SingleResult<ProductResponse>>
     **/
    @PostMapping
    @ApiOperation(value = "제품 등록")
    public ResponseEntity<SingleResult<ProductResponse>> createProduct(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "제품 사진", required = true)
            @RequestPart(value = "file") List<MultipartFile> multipartFile,
            @ApiParam(value = "제품 정보", required = true)
            @RequestPart(value = "product") @Valid ProductRequest productRequest, BindingResult bindingResult) throws IOException {

        productService.validateUploadForm(bindingResult);
        productService.validateFileExists(multipartFile);
        String[] splitToken = accessToken.split(" ");
        ProductResponse productResponse = productService.createProduct(splitToken[1], productRequest, multipartFile);

        return new ResponseEntity<>(responseService.getSingleResult
                (productResponse), HttpStatus.CREATED);
    }

    /**
     * @Method : updateProduct
     * @Description : 상품 정보 수정
     * @Parameter : [accessToken, productId, multipartFile, productUpdateRequest, bindingResult]
     * @Return : ResponseEntity<SingleResult<ProductResponse>>
     **/
    @PatchMapping("/{productId}")
    @ApiOperation(value = "제품 수정")
    public ResponseEntity<SingleResult<ProductResponse>> updateProduct(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "제품 아이디", required = true)
            @PathVariable Long productId,
            @ApiParam(value = "제품 사진", required = true)
            @RequestPart(value = "file") List<MultipartFile> multipartFile,
            @ApiParam(value = "제품 수정 정보", required = true)
            @RequestPart(value = "product") @Valid ProductUpdateRequest productUpdateRequest, BindingResult bindingResult) throws IOException {
        productService.validateUploadForm(bindingResult);
        String[] splitToken = accessToken.split(" ");
        ProductResponse productResponse = productService.updateProductById(productId, splitToken[1], multipartFile, productUpdateRequest);
        return new ResponseEntity<>(responseService.getSingleResult(productResponse), HttpStatus.CREATED);
    }

    /**
     * @Method : deleteProductById
     * @Description : 상품 삭제
     * @Parameter : [accessToken, productId]
     * @Return : ResponseEntity<CommonResult>
     **/
    @DeleteMapping("/{productId}")
    @ApiOperation(value = "제품 삭제")
    public ResponseEntity<CommonResult> deleteProductById(
            @ApiParam(value = "액세스 토큰", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "제품 아이디", required = true)
            @PathVariable long productId) {
        String[] splitToken = accessToken.split(" ");
        productService.deleteProductById(productId, splitToken[1]);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
}