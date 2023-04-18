package com.gankao.crosssdk

import com.gankao.network.api.BaseApi
import com.gankao.network.api.ResultCallback
import io.reactivex.Single

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 17:22
 *
 */
object TestApi: BaseApi<TestApiService>() {
    override fun getApi(): TestApiService {
        return initApi("")
    }

    // 处理数据
    fun <T> getData(single: Single<TestResponse<T>>, resultCallback: ResultCallback<T?>) {
        getData(single, { it?.result?.data }, resultCallback)
    }

    // 处理数据
    fun <T> getData(
        single: Single<TestResponse<T>>,
        onSuccess: (T?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getData(single, { it?.result?.data }, onSuccess, onError)
    }

    // 扩展函数，调用更爽
    fun <T> Single<TestResponse<T>>.testStart(
        onSuccess: (T?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getData(this, { onSuccess(it) }, { onError(it) })
    }
}