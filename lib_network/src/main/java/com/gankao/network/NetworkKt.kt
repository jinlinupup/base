package com.gankao.network

import java.net.SocketTimeoutException
import javax.net.ssl.SSLProtocolException


fun Throwable.getExceptionStr(): String {
    return when (this) {
        is SocketTimeoutException -> {
            "接口超时"
        }
        is SSLProtocolException -> {
            "证书验证失败"
        }

        else -> {
            "未知错误"
        }
    }
}