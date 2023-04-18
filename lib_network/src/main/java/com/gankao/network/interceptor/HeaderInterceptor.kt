package com.gankao.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 16:52
 *
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")
        return chain.proceed(builder.build())
    }
}