package com.gankao.crosssdk

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 17:26
 *
 */
data class TestResponse<T>(
    var errorCode: Int? = null,
    var data: T? = null,
    var errorMsg: String? = null,
)
