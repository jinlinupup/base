package com.gankao.crosssdk.vp2

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 11:56
 *
 */
data class TabEntity(val title: String, val index: Int) {
    override fun hashCode(): Int {
        return index
    }
}
