package com.ireland.ager.main.common

import org.springframework.data.domain.Slice

/**
 * @Class : SliceResult
 * @Description : 메인 도메인에 대한 다중 결과값 매핑
 */
class SliceResult<T> : CommonResult() {
    var data: Slice<T>? = null
}