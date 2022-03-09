package com.ireland.ager.config

import com.ireland.ager.account.exception.ExpiredAccessTokenException
import com.ireland.ager.account.exception.NotFoundTokenException
import com.ireland.ager.account.exception.UnAuthorizedAccessException
import com.ireland.ager.account.exception.UnAuthorizedTokenException
import com.ireland.ager.board.exception.InvalidBoardDetailException
import com.ireland.ager.board.exception.InvalidBoardTitleException
import com.ireland.ager.chat.exception.UnAuthorizedChatException
import com.ireland.ager.main.common.CommonResponse
import com.ireland.ager.main.common.CommonResult
import com.ireland.ager.main.common.service.ResponseService
import com.ireland.ager.main.exception.IntenalServerErrorException
import com.ireland.ager.main.exception.NotFoundException
import com.ireland.ager.product.exception.InvaildDataException
import com.ireland.ager.product.exception.InvaildFileExtensionException
import com.ireland.ager.product.exception.InvaildFormException
import com.ireland.ager.product.exception.InvaildProductCategoryException
import com.ireland.ager.product.exception.InvaildProductDetailException
import com.ireland.ager.product.exception.InvaildProductPriceException
import com.ireland.ager.product.exception.InvaildProductStatusException
import com.ireland.ager.product.exception.InvaildProductTitleException
import com.ireland.ager.product.exception.InvaildUploadException
import com.ireland.ager.review.exception.DuplicateReviewException
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @Class : ExceptionAdvice
 * @Description : 도메인에 대한 공통 에러 처리
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
class ExceptionAdvice {
    private val responseService: ResponseService? = null
    val log = LoggerFactory.getLogger(javaClass)
    @ExceptionHandler(ExpiredAccessTokenException::class)
    fun expiredAccessTokenException(e: ExpiredAccessTokenException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.EXPIREDACCESSTOKEN)
        log.error("[ERROR]:ExpiredAccessTokenException")
        return ResponseEntity(commonResult, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(e: NotFoundException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.NOTFOUND)
        log.error("[ERROR]:NotFoundException")
        return ResponseEntity(commonResult, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UnAuthorizedAccessException::class)
    fun unAuthorizedAccessException(e: UnAuthorizedAccessException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.UNAUTHORIZEDACCESS)
        log.error("[ERROR]:UnAuthorizedAccessException")
        return ResponseEntity(commonResult, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvaildUploadException::class)
    fun invaildUploadException(e: InvaildUploadException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDUPLOAD)
        log.error("[ERROR]:ExpiredAccessTokenException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvaildDataException::class)
    fun invaildDataException(e: InvaildDataException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDDATA)
        log.error("[ERROR]:InvaildDataException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvaildFileExtensionException::class)
    fun invaildFileExtensionException(e: InvaildFileExtensionException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDFILEEXTENSION)
        log.error("[ERROR]:InvaildFileExtensionException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IntenalServerErrorException::class)
    fun intenalServerErrorException(e: IntenalServerErrorException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INTERNALSERVERERROR)
        log.error("[ERROR]:IntenalServerErrorException")
        return ResponseEntity(commonResult, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(UnAuthorizedTokenException::class)
    fun unAuthorizedTokenException(e: UnAuthorizedTokenException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.UNAUTHORIZEDTOKEN)
        log.error("[ERROR]:UnAuthorizedTokenException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundTokenException::class)
    fun unAuthorizedTokenException(e: NotFoundTokenException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.NOTFOUNDTOKEN)
        log.error("[ERROR]:NotFoundTokenException")
        return ResponseEntity(commonResult, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UnAuthorizedChatException::class)
    fun unAuthorizedChatException(e: UnAuthorizedChatException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.UNAUTHORIZEDCHAT)
        log.error("[ERROR]:UnAuthorizedChatException")
        return ResponseEntity(commonResult, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(InvaildFormException::class)
    fun invaildFormException(e: InvaildFormException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDFORM)
        log.error("[ERROR]:InvaildFormException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvaildProductTitleException::class)
    fun invaildProductTitleException(e: InvaildProductTitleException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDPRODUCTTITLE)
        log.error("[ERROR]:InvaildProductTitleException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvaildProductPriceException::class)
    fun invaildProductPriceException(e: InvaildProductPriceException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDPRODUCTPRICE)
        log.error("[ERROR]:InvaildProductPriceException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvaildProductDetailException::class)
    fun invaildProductDetailException(e: InvaildProductDetailException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDPRODUCTDETAIL)
        log.error("[ERROR]:InvaildProductDetailException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvaildProductCategoryException::class)
    fun InvaildProductCategoryException(e: InvaildProductCategoryException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDPRODUCTCATEGORY)
        log.error("[ERROR]:InvaildProductCategoryException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvaildProductStatusException::class)
    fun InvaildProductStatusException(e: InvaildProductStatusException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDPRODUCTSTATUS)
        log.error("[ERROR]:InvaildProductStatusException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DuplicateReviewException::class)
    fun DuplicateReviewException(e: DuplicateReviewException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.DUPLICATEREVIEW)
        log.error("[ERROR]:DuplicateReviewException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidBoardTitleException::class)
    fun InvalidBoardTitleException(e: InvalidBoardTitleException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDBOARDTITLE)
        log.error("[ERROR]:InvalidBoardTitleException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidBoardDetailException::class)
    fun InvalidBoardTitleException(e: InvalidBoardDetailException?): ResponseEntity<CommonResult?> {
        val commonResult = responseService!!.getFailResult(CommonResponse.INVALIDBOARDDETAIL)
        log.error("[ERROR]:InvalidBoardDetailException")
        return ResponseEntity(commonResult, HttpStatus.BAD_REQUEST)
    }
}