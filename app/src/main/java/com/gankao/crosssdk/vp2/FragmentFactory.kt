package com.gankao.crosssdk.vp2

import androidx.fragment.app.Fragment
import com.gankao.crosssdk.vp2.fragment.BlankFragment

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 11:51
 *
 */
object FragmentFactory {
    //pos 回调
    fun genFragment(index: Int,call: ((Boolean)->Unit)?=null): Fragment {
        return when (index) {
            0 -> {
                BlankFragment.newInstance("1", "2").apply {
                    //回调
                }
            }
            1 -> {
                BlankFragment.newInstance("1", "2").apply {
                    //回调
                }
            }
            else -> {
                BlankFragment.newInstance("1", "2").apply {
                    //回调
                }
            }
        }
    }
}