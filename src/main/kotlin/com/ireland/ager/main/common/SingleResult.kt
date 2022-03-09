package com.ireland.ager.main.common

/**
 * @Class : SingleResult
 * @Description : 메인 도메인에 대한 단일 결과값 매핑
 */
class SingleResult<T> : CommonResult() {
    var data: T? = null
}