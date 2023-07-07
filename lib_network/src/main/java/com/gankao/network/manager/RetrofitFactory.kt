package com.gankao.network.manager

import com.gankao.network.LogUtil
import com.gankao.network.interceptor.HeaderInterceptor
import com.gankao.network.interceptor.PLBInterceptor
import com.hjq.gson.factory.GsonFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 16:47
 *
 */
class RetrofitFactory {
    private val baseUrlMap = hashMapOf<String, Retrofit>()

    private val TIMEOUT = 30 * 1000L

    fun <T : Any> getApiService(baseUrl: String, serviceClass: Class<T>): T {
        var retrofit = baseUrlMap[baseUrl]
        if (retrofit == null) {
            retrofit = createRetrofit(baseUrl)
            baseUrlMap[baseUrl] = retrofit
        }
        return retrofit.create(serviceClass)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSingletonGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor { message -> LogUtil.i("okhttp: $message") }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(PLBInterceptor())
            .build()
        //perHost最大链接数(默认是5)
        okHttpClient.dispatcher.maxRequestsPerHost = 10
        return okHttpClient
    }
}