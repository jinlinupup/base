package cn.gankao.bbbcheck.api

import cn.gankao.bbbcheck.draw.PageInfo
import io.reactivex.Single
import retrofit2.http.*

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-18-2023 周二 17:24
 *
 */
interface TestApiService {
    @POST("g31k")
    fun getPenData(@Body bean: Any,@Header("authorization") token:String): Single<TestResponse<PageInfo>>
}