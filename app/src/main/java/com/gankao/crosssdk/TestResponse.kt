package com.gankao.crosssdk

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 17:26
 *
 */
data class TestResponse<T>(
    var err: Any? = null,
    var result: ResultBean<T>? = null,
    var __timestamp: Long? = 0,
    var __runtime: Int? = 0,
)

data class ResultBean<T>(
    var data: T? = null
)