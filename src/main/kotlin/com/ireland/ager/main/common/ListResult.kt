package com.ireland.ager.main.common

/**
 * @Class : ListResult
 * @Description : 메인 도메인에 대한 리스트 결과값 매핑
 */
class ListResult<T> : CommonResult() {
    var data: List<T>? = null
}