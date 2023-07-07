package com.gankao.crosssdk.vp2

import androidx.recyclerview.widget.DiffUtil

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 11:50
 *
 */
interface IViewPagerBasic<T,L> {
    fun addFragment(addFragmentTab: T, insertPos: Int = -1, commitCallback: Runnable? = null) {
    }
    fun removeFragment(removeFragmentTab: T, commitCallback: Runnable? = null) {
    }
    fun removeFragment(pos: Int, commitCallback: Runnable? = null) {
    }
    fun genDiffItemCall(): DiffUtil.ItemCallback<T>
    fun fragmentTag(pos: Int):String
    fun setFragmentListener(l: ((L)->Unit)?=null){
    }
}