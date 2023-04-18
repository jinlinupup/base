package com.gankao.network.api

import com.gankao.network.LogUtil
import com.gankao.network.isTRUE
import com.gankao.network.manager.RetrofitManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * 基类
 * 使用时可以实现此类，自己创建方法实现自己的 Api 对象
 * 封装自己的请求。
 */
abstract class BaseApi<ApiSerVice : Any> {

    //  网络请求容器，可通过此容器一次性取消所有请求
    val compDisposable = CompositeDisposable()

    /**
     * 底层处理，上层传入 对应 server api 路径即可 例子：【 xxx/ 】 =>  【 api-source/ 】一定要以 / 结尾
     * 如果传入全路径（http开头） 则不拦截拼接。（兼容特殊情况）
     */
    fun initApi(apiPath: String): ApiSerVice {
        var url = apiPath
        if (apiPath.startsWith("http://", true) || apiPath.startsWith("https://", true)) {
            // 是否进行拦截，需要再处理，不需要，不拦截 ？？？？
            LogUtil.d("BASE_URL: 处理：${apiPath}")
        } else {
            url = "${getBaseUrlByEnv()}${apiPath}"
        }
        return RetrofitManager.get().getApiService(url, getFirstGenericClazz())
    }

    /**
     * 获取环境变量，动态获取 base_url
     */
    protected fun getBaseUrlByEnv(): String {
//        try {
//            val current = SPUtils.getInstance(BaseApp.application).getString("app_")
//            if ("preview" == current) {
//                return BASE_URL_PREVIEW
//            } else if ("test" == current) {
//                return BASE_URL_TEST
//            }
//        } catch (e: Exception) {
//            return BASE_URL_RELEASE
//        }
        return "BASE_URL_RELEASE"
    }

    abstract fun getApi(): ApiSerVice

    @Suppress("UNCHECKED_CAST")
    fun getFirstGenericClazz(): Class<ApiSerVice> {
        (this.javaClass.genericSuperclass is ParameterizedType).isTRUE {
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.forEach {
                try {
                    return@getFirstGenericClazz (it as Class<ApiSerVice>)
                } catch (e: NoSuchMethodException) {
                } catch (e: ClassCastException) {
                } catch (e: InvocationTargetException) {
                    throw e.targetException
                }
            }
        }
        throw NullPointerException("Cannot find genericSuperclass, please check your code is not wrong")
    }

    /**
     * 处理数据,这里可以统一处理请求->集中中断。
     */
    fun <ResponseT, ResultR> getData(
        single: Single<ResponseT>,
        run: (t: ResponseT?) -> ResultR?,
        callBack: ResultCallback<ResultR?>
    ): Disposable {
        val disposable = single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t ->
                callBack.onSuccess(run(t))
            }, {
                callBack.onError(it)
            })
        compDisposable.add(disposable)
        return disposable
    }

    /**
     * 处理数据,这里可以同意 处理请求中断情况。
     * @param ResponseT 泛型 为接口中返回的 泛型类型
     * @param ResultR 泛型 为返回值 经过 run(T) 处理后的类型
     */
    fun <ResponseT, ResultR> getData(
        single: Single<ResponseT>,
        run: (t: ResponseT?) -> ResultR?,
        onSuccess: (ResultR?) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        val disposable = single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t ->
                onSuccess(run(t))
            }, {
                onError(it)
            })
        compDisposable.add(disposable)
        return disposable
    }

    fun dispose() {
        compDisposable.clear()
    }
}


interface ResultCallback<ResultR> {
    fun onSuccess(result: ResultR?) //成功

    fun onError(e: Throwable) //失败
}

