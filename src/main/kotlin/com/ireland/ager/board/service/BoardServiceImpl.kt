package com.ireland.ager.board.service

import com.ireland.ager.chat.dto.request.MessageRequest
import com.ireland.ager.chat.dto.request.MessageRequest.MessageRequestBuilder
import com.ireland.ager.chat.entity.MessageRoom
import com.ireland.ager.chat.dto.response.RoomCreateResponse.RoomCreateResponseBuilder
import com.ireland.ager.chat.dto.response.RoomCreateResponse
import com.ireland.ager.chat.dto.response.MessageDetailsResponse.MessageDetailsResponseBuilder
import com.ireland.ager.chat.dto.response.MessageDetailsResponse
import com.ireland.ager.chat.dto.response.MessageSummaryResponse.MessageSummaryResponseBuilder
import com.ireland.ager.chat.dto.response.MessageSummaryResponse
import com.ireland.ager.account.entity.Account
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import com.ireland.ager.chat.config.KafkaConstants
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import lombok.extern.slf4j.Slf4j
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import com.ireland.ager.config.BaseEntity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.JoinColumn
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Enumerated
import com.ireland.ager.chat.entity.ReviewStatus
import com.ireland.ager.chat.entity.RoomStatus
import com.ireland.ager.chat.entity.MessageRoom.MessageRoomBuilder
import lombok.RequiredArgsConstructor
import com.ireland.ager.chat.repository.MessageRoomRepository
import com.ireland.ager.chat.repository.MessageRepository
import com.ireland.ager.account.service.AccountServiceImpl
import com.ireland.ager.product.service.ProductServiceImpl
import com.ireland.ager.account.exception.UnAuthorizedAccessException
import com.ireland.ager.chat.exception.UnAuthorizedChatException
import org.springframework.messaging.simp.SimpMessagingTemplate
import com.ireland.ager.chat.service.MessageService
import org.springframework.kafka.annotation.KafkaListener
import com.ireland.ager.chat.service.KafkaConsumerService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import com.ireland.ager.chat.service.KafkaProductService
import org.springframework.web.bind.annotation.PostMapping
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.handler.annotation.DestinationVariable
import com.ireland.ager.main.common.service.ResponseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.http.ResponseEntity
import com.ireland.ager.main.common.SliceResult
import org.springframework.web.bind.annotation.PathVariable
import com.ireland.ager.main.common.SingleResult
import org.springframework.web.bind.annotation.DeleteMapping
import com.ireland.ager.main.common.CommonResult
import org.springframework.data.jpa.repository.JpaRepository
import com.ireland.ager.chat.repository.MessageRepositoryCustom
import com.querydsl.jpa.impl.JPAQueryFactory
import com.ireland.ager.chat.entity.QMessage
import org.springframework.data.domain.SliceImpl
import com.ireland.ager.chat.repository.MessageRoomRepositoryCustom
import com.ireland.ager.chat.entity.QMessageRoom
import com.ireland.ager.main.common.ListResult
import com.ireland.ager.main.common.CommonResponse
import lombok.AllArgsConstructor
import com.ireland.ager.main.entity.Search
import com.ireland.ager.main.entity.Search.SearchBuilder
import org.springframework.data.redis.core.RedisTemplate
import com.ireland.ager.main.repository.SearchRepository
import org.springframework.data.redis.core.ListOperations
import com.ireland.ager.main.service.SearchService
import org.springframework.cache.annotation.CacheEvict
import com.amazonaws.services.s3.AmazonS3Client
import kotlin.Throws
import com.amazonaws.AmazonServiceException
import com.ireland.ager.product.entity.Url
import com.ireland.ager.main.service.UploadServiceImpl
import com.ireland.ager.board.entity.BoardUrl
import org.springframework.web.multipart.MultipartFile
import com.ireland.ager.product.exception.InvaildFileExtensionException
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.Upload
import net.coobird.thumbnailator.Thumbnails
import com.ireland.ager.board.service.BoardServiceImpl
import org.springframework.web.bind.annotation.RequestParam
import com.ireland.ager.product.dto.response.ProductThumbResponse
import com.ireland.ager.board.dto.response.BoardSummaryResponse
import com.ireland.ager.main.repository.SearchRepositoryCustom
import com.ireland.ager.main.entity.QSearch
import com.ireland.ager.board.entity.Board
import com.ireland.ager.board.dto.request.BoardRequest
import com.ireland.ager.board.dto.request.CommentRequest
import com.ireland.ager.board.dto.response.BoardResponse
import com.ireland.ager.board.dto.response.CommentResponse
import javax.persistence.FetchType
import com.ireland.ager.board.entity.Board.BoardBuilder
import com.ireland.ager.board.entity.Comment.CommentBuilder
import com.ireland.ager.board.entity.BoardUrl.BoardUrlBuilder
import com.ireland.ager.board.repository.BoardRepository
import org.springframework.data.redis.core.ValueOperations
import com.ireland.ager.product.exception.InvaildUploadException
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import com.ireland.ager.board.exception.InvalidBoardTitleException
import com.ireland.ager.board.exception.InvalidBoardDetailException
import com.ireland.ager.board.repository.CommentRepository
import org.springframework.web.bind.annotation.RequestPart
import javax.validation.Valid
import org.springframework.web.bind.annotation.PatchMapping
import com.ireland.ager.board.service.CommentServiceImpl
import com.ireland.ager.board.repository.BoardRepositoryCustom
import com.ireland.ager.board.repository.CommentRepositoryCustom
import com.querydsl.jpa.impl.JPAQuery
import com.ireland.ager.board.entity.QBoard
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.core.types.OrderSpecifier
import com.ireland.ager.board.entity.QComment
import com.ireland.ager.board.entity.QBoardUrl
import com.querydsl.core.types.dsl.BooleanExpression
import com.ireland.ager.product.entity.QProduct
import com.ireland.ager.trade.entity.Trade
import com.ireland.ager.trade.entity.Trade.TradeBuilder
import com.ireland.ager.product.repository.ProductRepository
import com.ireland.ager.trade.repository.TradeRepository
import com.ireland.ager.product.entity.ProductStatus
import com.ireland.ager.trade.service.TradeServiceImpl
import com.ireland.ager.trade.repository.TradeRepositoryCustom
import com.ireland.ager.trade.entity.QTrade
import org.springframework.web.servlet.HandlerInterceptor
import com.ireland.ager.account.service.AuthServiceImpl
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.ireland.ager.config.interceptor.KakaoAuthenticationInterceptor
import org.springframework.util.PatternMatchUtils
import javax.persistence.EntityListeners
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.annotation.CreatedDate
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.cache.CacheManager
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.cache.RedisCacheManager
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.spi.DocumentationType
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.PathSelectors
import javax.persistence.EntityManager
import org.springframework.web.bind.annotation.RestControllerAdvice
import com.ireland.ager.account.exception.ExpiredAccessTokenException
import com.ireland.ager.config.ExceptionAdvice
import com.ireland.ager.product.exception.InvaildDataException
import com.ireland.ager.main.exception.IntenalServerErrorException
import com.ireland.ager.account.exception.UnAuthorizedTokenException
import com.ireland.ager.account.exception.NotFoundTokenException
import com.ireland.ager.product.exception.InvaildFormException
import com.ireland.ager.product.exception.InvaildProductTitleException
import com.ireland.ager.product.exception.InvaildProductPriceException
import com.ireland.ager.product.exception.InvaildProductDetailException
import com.ireland.ager.product.exception.InvaildProductCategoryException
import com.ireland.ager.product.exception.InvaildProductStatusException
import com.ireland.ager.review.exception.DuplicateReviewException
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.web.cors.CorsConfiguration
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import com.ireland.ager.review.dto.request.ReviewRequest
import com.ireland.ager.review.entity.Review
import com.ireland.ager.review.dto.response.ReviewResponse
import com.ireland.ager.review.dto.response.ReviewResponse.ReviewResponseBuilder
import com.ireland.ager.review.entity.Review.ReviewBuilder
import com.ireland.ager.review.repository.ReviewRepository
import com.ireland.ager.review.service.ReviewServiceImpl
import com.ireland.ager.review.repository.ReviewRepositoryCustom
import com.ireland.ager.review.entity.QReview
import com.ireland.ager.account.dto.request.AccountUpdateRequest
import com.ireland.ager.account.dto.response.KakaoResponse.KakaoAccount
import com.ireland.ager.account.dto.response.KakaoResponse
import com.ireland.ager.account.dto.response.MyAccountResponse
import com.ireland.ager.account.dto.response.MyAccountResponse.MyAccountResponseBuilder
import com.ireland.ager.account.dto.response.OtherAccountResponse
import com.ireland.ager.account.dto.response.OtherAccountResponse.OtherAccountResponseBuilder
import com.ireland.ager.account.repository.AccountRepository
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpClientErrorException
import com.ireland.ager.account.service.AccountInfoServiceImpl
import com.ireland.ager.product.dto.request.ProductRequest
import com.ireland.ager.product.dto.request.ProductUpdateRequest
import com.ireland.ager.product.dto.response.ProductResponse
import com.ireland.ager.product.dto.response.ProductResponse.ProductResponseBuilder
import com.ireland.ager.product.dto.response.ProductThumbResponse.ProductThumbResponseBuilder
import com.ireland.ager.product.entity.Url.UrlBuilder
import com.ireland.ager.product.entity.Product.ProductBuilder
import com.ireland.ager.product.repository.ProductRepositoryCustom
import com.ireland.ager.product.entity.QUrl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.web.firewall.HttpFirewall
import org.springframework.security.web.firewall.StrictHttpFirewall
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import com.ireland.ager.AgerProjectApplication
import com.ireland.ager.main.exception.NotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.IOException
import java.lang.IllegalStateException
import java.time.Duration
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import javax.transaction.Transactional

/**
 * @Class : BoardServiceImpl
 * @Description : 게시판 도메인에 대한 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
class BoardServiceImpl {
    private val boardRepository: BoardRepository? = null
    private val accountService: AccountServiceImpl? = null
    private val uploadService: UploadServiceImpl? = null
    private val redisTemplate: RedisTemplate<*, *>? = null
    private val searchRepository: SearchRepository? = null

    /**
     * @Method : createPost
     * @Description : 게시물 생성
     * @Parameter : [accessToken, boardRequest, multipartFile]
     * @Return : BoardResponse
     */
    @Throws(IOException::class)
    fun createPost(
        accessToken: String?,
        boardRequest: BoardRequest?,
        multipartFile: List<MultipartFile>
    ): BoardResponse {
        val account = accountService!!.findAccountByAccessToken(accessToken)
        println(multipartFile.size)
        val uploadImagesUrl = uploadService!!.uploadImages(multipartFile)
        val newPost =
            boardRepository!!.save<Board>(BoardRequest.Companion.toBoard(boardRequest, account, uploadImagesUrl))
        return BoardResponse.Companion.toBoardResponse(newPost, account)
    }

    /**
     * @Method : updatePost
     * @Description : 게시물 수정
     * @Parameter : [accessToken, boardId, boardRequest, multipartFile]
     * @Return : BoardResponse
     */
    fun updatePost(
        accessToken: String?,
        boardId: Long?,
        boardRequest: BoardRequest,
        multipartFile: List<MultipartFile>
    ): BoardResponse {
        val board = findPostById(boardId)
        val account = accountService!!.findAccountByAccessToken(accessToken)
        if (account != board.getAccountId()) {
            throw UnAuthorizedAccessException()
        }
        validateFileExists(multipartFile)
        val currentFileImageUrlList = board.getUrlList()
        uploadService!!.deleteBoard(currentFileImageUrlList)
        board!!.deleteUrl()
        var updateFileImageUrlList: List<String?>? = ArrayList()
        try {
            updateFileImageUrlList = uploadService.uploadImages(multipartFile)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val toBoardUpdate = boardRequest.toBoardUpdate(board, updateFileImageUrlList)
        boardRepository!!.save(toBoardUpdate)
        return BoardResponse.Companion.toBoardResponse(toBoardUpdate, account)
    }

    /**
     * @Method : deletePost
     * @Description : 게시물 삭제
     * @Parameter : [accessToken, boardId]
     * @Return : null
     */
    fun deletePost(accessToken: String?, boardId: Long) {
        val board = boardRepository!!.findById(boardId).orElseThrow(
            Supplier<NotFoundException> { NotFoundException() })!!
        val account = accountService!!.findAccountByAccessToken(accessToken)
        if (account != board.accountId) {
            throw UnAuthorizedAccessException()
        }
        uploadService!!.deleteBoard(board.urlList)
        boardRepository.deleteById(board.boardId)
    }

    /**
     * @Method : findPostById
     * @Description : 게시물 아이디로 게시물 조회
     * @Parameter : [boardId]
     * @Return : Board
     */
    @Cacheable(value = ["board"])
    fun findPostById(boardId: Long?): Board? {
        return boardRepository!!.addViewCnt(boardId)
    }

    /**
     * @Method : addViewCntToRedis
     * @Description : 레디스 서버 게시물 조회수 증가
     * @Parameter : [boardId]
     * @Return : null
     */
    fun addViewCntToRedis(boardId: Long) {
        val key = "boardViewCnt::$boardId"
        val valueOperations = redisTemplate!!.opsForValue()
        if (valueOperations[key] == null) valueOperations.set(
            key, boardRepository!!.findBoardViewCnt(boardId).toString(),
            Duration.ofMinutes(5)
        ) else valueOperations.increment(key)
    }

    /**
     * @Method : deleteViewCntCacheFromRedis
     * @Description : 레디스 서버 조회수 캐시 삭제
     * @Parameter : []
     * @Return : null
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    fun deleteViewCntCacheFromRedis() {
        val redisKeys: Set<String> = redisTemplate!!.keys("boardViewCnt*")
        val it = redisKeys.iterator()
        while (it.hasNext()) {
            val data = it.next()
            val boardId = data.split("::").toTypedArray()[1].toLong()
            val viewCnt: Long = redisTemplate.opsForValue()[data] as String?. toLong ()
            boardRepository!!.addViewCntFromRedis(boardId, viewCnt)
            redisTemplate.delete(data)
            redisTemplate.delete("board::$boardId")
        }
    }

    /**
     * @Method : findBoardAllByCreatedAtDesc
     * @Description : 모든 게시물 내림차순 조회
     * @Parameter : [keyword, pageable]
     * @Return : Slice<BoardSummaryResponse>
    </BoardSummaryResponse> */
    fun findBoardAllByCreatedAtDesc(keyword: String?, pageable: Pageable?): Slice<BoardSummaryResponse?>? {
        if (!isStringEmpty(keyword)) searchRepository!!.save(Search(keyword))
        return boardRepository!!.findAllBoardPageableOrderByCreatedAtDesc(keyword!!, pageable!!)
    }

    fun isStringEmpty(str: String?): Boolean {
        return str == null || str.trim { it <= ' ' }.length == 0
    }

    /**
     * @Method : validateFileExists
     * @Description : 이미지 파일 유효성 검증
     * @Parameter : [file]
     * @Return : null
     */
    fun validateFileExists(file: List<MultipartFile>) {
        if (file.isEmpty()) throw InvaildUploadException()
    }

    /**
     * @Method : validateUploadForm
     * @Description : 게시물 업로드 유효성 검증
     * @Parameter : [bindingResult]
     * @Return : null
     */
    fun validateUploadForm(bindingResult: BindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.allErrors.forEach(Consumer { objectError: ObjectError ->
                val errorCode = objectError.defaultMessage
                when (Objects.requireNonNull(errorCode)) {
                    "3110" -> throw InvalidBoardTitleException()
                    "3130" -> throw InvalidBoardDetailException()
                }
            })
        }
    }
}