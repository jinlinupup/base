package com.gankao.crosssdk.base

import com.github.mikephil.charting.data.Entry

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-25-2023 周日 17:40
 *
 */
data class ChatBean(
    val number:Int,
    val name:String,
)

data class WrongBean(
    val eNumber:Int,
    val sNumber:Int,
    val name:String,
)