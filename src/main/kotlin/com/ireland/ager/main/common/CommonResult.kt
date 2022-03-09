package com.ireland.ager.main.common

/**
 * @Class : CommonResult
 * @Description : 메인 도메인에 대한 결과 매핑
 */
open class CommonResult(
    var success: Boolean = false,
    var code: Int = 0,
    var msg: String? = null
)