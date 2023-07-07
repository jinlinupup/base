package com.gankao.network.interceptor

import com.gankao.network.LogUtil
import okhttp3.*
import okio.Buffer
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 01-06-2023 周五 14:12
 * ┌─┐       ┌─┐ + +
 * ┌──┘ ┴───────┘ ┴──┐++
 * │                 │
 * │       ───       │++ + + +
 * ███████───███████ │+
 * │                 │+
 * │       ─┴─       │
 * │                 │
 * └───┐         ┌───┘
 * │         │
 * │         │   + +
 * │         │
 * │         └──────────────┐
 * │                        │
 * │                        ├─┐
 * │                        ┌─┘
 * │                        │
 * └─┐  ┐  ┌───────┬──┐  ┌──┘  + + + +
 * │ ─┤ ─┤       │ ─┤ ─┤
 * └──┴──┘       └──┴──┘  + + + +
 */
class PLBInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val request: Request = builder.build()
        val originalResponse: Response = chain.proceed(request)
        //读取服务器返回的结果
        val responseBody = originalResponse.body

        LogUtil.e("PLBInterceptor", responseBody.toString())

        return originalResponse
    }


    fun getResponseBody(responseBody: ResponseBody?): String {
        if (responseBody == null) {
            return "";
        }
        val UTF8: Charset = Charset.forName("UTF-8")

        val source = responseBody.source()
        try {
            source.request(Long.MAX_VALUE) // Buffer the entire body.
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val buffer: Buffer = source.buffer()
        var charset: Charset = UTF8
        val contentType: MediaType? = responseBody.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8)!!
            } catch (e: UnsupportedCharsetException) {
                e.printStackTrace()
            }
        }
        return buffer.clone().readString(charset)
    }
}