package com.gankao.crosssdk

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 17:24
 *
 */
interface TestApiService {
    @POST("")
    fun test1(): Single<TestResponse<Any>>

    @POST("")
    fun test2(): Single<TestResponse<Any>>
}