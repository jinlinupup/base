package com.gankao.network.manager

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 16:58
 *
 */

class RetrofitManager private constructor() {

    private var mRetrofitFactory= RetrofitFactory()

    companion object {

        private val instance: RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitManager() }

        fun get(): RetrofitManager {
            return instance
        }
    }

    fun <T : Any> getApiService(baseUrl:String, serviceClass: Class<T>): T {
        return mRetrofitFactory.getApiService(baseUrl,serviceClass)
    }

}
