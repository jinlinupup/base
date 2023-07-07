package com.gankao.common.throwable

/**
* @Description TODO
* @Author sujinlin
* @Date 04-27-2023 周四 17:16
*
*/class CustomException : Exception() {
    init {
        println("-----------收到异常----------")
        println("错误日志：${this.message}")
        println("todo 上传错误日志到服务器")
        println("-----------异常结束----------")
    }
}