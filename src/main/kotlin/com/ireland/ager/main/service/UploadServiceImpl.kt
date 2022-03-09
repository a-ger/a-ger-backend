package com.ireland.ager.main.service

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManager
import com.ireland.ager.board.entity.BoardUrl
import com.ireland.ager.product.entity.Url
import com.ireland.ager.product.exception.InvaildFileExtensionException
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import net.coobird.thumbnailator.Thumbnails
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

/**
 * @Class : UploadServiceImpl
 * @Description : S3 이미지 업로드 도메인에 대한 서비스
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
class UploadServiceImpl {
    @Value("\${cloud.aws.s3.bucket.url}")
    private val defaultUrl: String? = null

    @Value("\${cloud.aws.s3.bucket.name}")
    var bucket: String? = null
    private val amazonS3Client: AmazonS3Client? = null

    /**
     * @Method : delete
     * @Description : S3에서 상품 이미지 삭제
     * @Parameter : [currentFileImageUrlList, thumbNailUrl]
     * @Return : null
     */
    @Throws(AmazonServiceException::class)
    fun delete(currentFileImageUrlList: List<Url?>?, thumbNailUrl: String?) {
        try {
            amazonS3Client!!.deleteObject(bucket, thumbNailUrl!!.split("/").toTypedArray()[3])
            //log.info("삭제될 파일의 이름은 : {}", thumbNailUrl.split("/").toTypedArray()[3])
        } catch (e: AmazonServiceException) {
            e.printStackTrace()
        }
        for (url in currentFileImageUrlList!!) {
            amazonS3Client!!.deleteObject(bucket, url.url.split("/").toTypedArray()[3])
            //log.info("삭제될 파일의 이름은 : {}", url.getUrl().split("/").toTypedArray()[3])
        }
    }

    /**
     * @Method : deleteBoard
     * @Description : S3에서 게시물 이미지 삭제
     * @Parameter : [currentFileImageUrlList]
     * @Return : null
     */
    @Throws(AmazonServiceException::class)
    fun deleteBoard(currentFileImageUrlList: List<BoardUrl?>?) {
        for (url in currentFileImageUrlList!!) {
            amazonS3Client!!.deleteObject(bucket, url.getUrl().split("/").toTypedArray()[3])
            UploadServiceImpl.log.info("삭제될 파일의 이름은 : {}", url.getUrl().split("/").toTypedArray()[3])
        }
    }

    /**
     * @Method : uploadImages
     * @Description : S3 이미지 다중 파일 업로드
     * @Parameter : [uploadFiles]
     * @Return : List<String>
    </String> */
    @Throws(IOException::class)
    fun uploadImages(uploadFiles: List<MultipartFile>): List<String> {
        UploadServiceImpl.log.info("업로드 파일의 갯수 : {}", uploadFiles.size)
        val uploadUrl: MutableList<String> = ArrayList()
        for (uploadFile in uploadFiles) {
            uploadUrl.add(uploadImg(uploadFile))
        }
        return uploadUrl
    }

    /**
     * @Method : uploadImg
     * @Description : S3 이미지 단일 파일 업로드
     * @Parameter : [multipartFile]
     * @Return : String
     */
    @Throws(IOException::class)
    fun uploadImg(multipartFile: MultipartFile): String {
        val origName = multipartFile.originalFilename
        val ext = origName.substring(origName.lastIndexOf('.'))
        return if (ext == ".jpg" || ext == ".png" || ext == ".jpeg") {
            val saveFileName = uuid + ext
            val image = ImageIO.read(multipartFile.inputStream)
            uploadImgToS3(image, saveFileName, ext)
        } else throw InvaildFileExtensionException()
    }

    /**
     * @Method : uploadImgToS3
     * @Description : S3 이미지 업로드
     * @Parameter : [image, FileName, ext]
     * @Return : String
     */
    @Throws(IllegalStateException::class, IOException::class)
    fun uploadImgToS3(image: BufferedImage?, Filename: String, ext: String): String {
        val url: String
        try {
            val os = ByteArrayOutputStream()
            UploadServiceImpl.log.info("확장자는 :{}", ext.substring(1))
            ImageIO.write(image, ext.substring(1), os)
            val bytes = os.toByteArray()
            val objectMetadata = ObjectMetadata()
            objectMetadata.contentLength = bytes.size.toLong()
            objectMetadata.contentType = ext.substring(1)
            val byteArrayInputStream = ByteArrayInputStream(bytes)
            val transferManager = TransferManager(amazonS3Client)
            val request = PutObjectRequest(bucket, Filename, byteArrayInputStream, objectMetadata)
            val upload = transferManager.upload(request)
            upload.waitForCompletion()
        } catch (e: AmazonServiceException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        url = defaultUrl + Filename
        return url
    }

    /**
     * @Method : makeThumbNail
     * @Description : 썸네일 이미지 생성
     * @Parameter : [multipartFile]
     * @Return : String
     */
    @Throws(IOException::class)
    fun makeThumbNail(multipartFile: MultipartFile): String {
        val origName = multipartFile.originalFilename
        val ext = origName.substring(origName.lastIndexOf('.'))
        return if (ext == ".jpg" || ext == ".png" || ext == ".jpeg") {
            val saveFileName = uuid + ext
            val image = ImageIO.read(multipartFile.inputStream)
            val getWidth = image.width.toDouble()
            val getHeight = image.height.toDouble()
            val resizeRatio = getWidth / getHeight
            val mediumHeight = 100
            val mediumWidth = (resizeRatio * mediumHeight).toInt()
            val thumbnail_medium = Thumbnails.of(image).size(mediumWidth, mediumHeight).asBufferedImage()
            uploadImgToS3(thumbnail_medium, saveFileName, ext)
        } else throw InvaildFileExtensionException()
    }

    companion object {
        /**
         * @Method : getUuid
         * @Description : uuid 값 생성
         * @Parameter : []
         * @Return : String
         */
        private val uuid: String
            private get() = UUID.randomUUID().toString().replace("-".toRegex(), "")
    }
}