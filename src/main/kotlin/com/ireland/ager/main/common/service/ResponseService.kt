package com.ireland.ager.main.common.service

import com.ireland.ager.main.common.*
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

/**
 * @Class : ResponseService
 * @Description : 메인 도메인에 대한 서비스
 */
@Service
class ResponseService {
    /**
     * @Method : getSingleResult
     * @Description : 단일건 결과 처리
     * @Parameter : [data]
     * @Return : SingleResult<T>
    </T> */
    fun <T> getSingleResult(data: T): SingleResult<T> {
        val result = SingleResult<T>()
        result.data = data
        successResult = result
        return result
    }

    /**
     * @Method : getListResult
     * @Description : 복수건 결과 처리
     * @Parameter : [list]
     * @Return : ListResult<T>
    </T> */
    fun <T> getListResult(list: List<T>?): ListResult<T> {
        val result = ListResult<T>()
        result.data = list
        successResult = result
        return result
    }

    /**
     * @Method : getSliceResult
     * @Description : 페이징 결과 처리
     * @Parameter : [list]
     * @Return : SliceResult<T>
    </T> */
    fun <T> getSliceResult(list: Slice<T>?): SliceResult<T> {
        val result = SliceResult<T>()
        result.data = list
        successResult = result
        return result
    }
    /**
     * @Method : getSuccessResult
     * @Description : 성공 결과만 처리
     * @Parameter : []
     * @Return : CommonResult
     */
    /**
     * @Method : setSuccessResult
     * @Description : 결과에 api 요청 성공 데이터 세팅
     * @Parameter : [result]
     * @Return : null
     */
    var successResult: CommonResult
        get() {
            val result = CommonResult()
            successResult = result
            return result
        }
        set(result) {
            result.success = true
            result.code = CommonResponse.SUCCESS.code!!
            result.msg = CommonResponse.SUCCESS.msg
        }

    /**
     * @Method : getFailResult
     * @Description : 실패 처리
     * @Parameter : [commonResponse]
     * @Return : CommonResult
     */
    fun getFailResult(commonResponse: CommonResponse): CommonResult {
        val result = CommonResult()
        setFailResult(commonResponse, result)
        return result
    }

    /**
     * @Method : setFailResult
     * @Description : 실패 결과 바인딩
     * @Parameter : [commonResponse, result]
     * @Return : null
     */
    private fun setFailResult(commonResponse: CommonResponse, result: CommonResult) {
        result.success = false
        result.msg = commonResponse.msg
        result.code = commonResponse.code!!
    }
}