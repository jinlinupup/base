package com.gankao.crosssdk.vp2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 11:49
 *
 */
class MainViewPage2Adapter(fragmentActivity: FragmentActivity) :
    BaseDiffPager2Adapter<TabEntity,Boolean>(fragmentActivity) {
    override fun genDiffItemCall(): DiffUtil.ItemCallback<TabEntity> {
        return diffCallBack
    }

    override fun createFragment(position: Int): Fragment {
        //listener为回调
        return FragmentFactory.genFragment(position,listener)
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<TabEntity>() {
            override fun areItemsTheSame(oldItem: TabEntity, newItem: TabEntity): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: TabEntity, newItem: TabEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}