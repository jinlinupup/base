package com.gankao.crosssdk.vp2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 11:50
 *
 */
/**
 * 名称：支持 add  remove 的viewpager2 adapter,支持异步diff
 */
abstract class BaseDiffPager2Adapter<T, L> : FragmentStateAdapter, IViewPagerBasic<T, L> {
    private var mDiffer: AsyncListDiffer<T>? = null
    protected var listener: ((L) -> Unit)? = null

    constructor(fragment: Fragment) : super(fragment)
    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    init {
        mDiffer = AsyncListDiffer(this.getAdapter(), this.genDiffItemCall())
    }

    override fun setFragmentListener(l: ((L) -> Unit)?) {
        this.listener = l
    }

    override fun containsItem(itemId: Long): Boolean {
        mDiffer?.currentList?.forEach {
            if (it.hashCode().toLong() == itemId) {
                return true
            }
        }
        return false
    }

    override fun addFragment(addFragmentTab: T, insertPos: Int, commitCallback: Runnable?) {
        val arrayList = getMutablePageList()
        if (!arrayList.contains(addFragmentTab)) {
            val inPos = if (insertPos != -1 && insertPos <= itemCount) insertPos else itemCount
            arrayList.add(inPos, addFragmentTab)
            mergeList(arrayList, commitCallback)
        }

    }

    override fun removeFragment(removeFragmentTab: T, commitCallback: Runnable?) {
        removeFragmentTab?.let {
            val arrayList = getMutablePageList()
            val index = arrayList.indexOf(it)
            if (index != -1) {
                arrayList.remove(it)
                mergeList(arrayList, commitCallback)
            }
        }
    }

    override fun removeFragment(pos: Int, commitCallback: Runnable?) {
        val arrayList = getMutablePageList()
        if (pos < arrayList.size) {
            removeFragment(arrayList[pos], commitCallback)
        }

    }

    override fun getItemCount(): Int {
        return mDiffer?.currentList?.size ?: 0
    }

    fun setNewData(list: ArrayList<T>) {
        mergeList(list)
    }

    fun mergeList(mNewList: ArrayList<T>, commitCallback: Runnable? = null) {
        val arrayList = getMutablePageList()
        if (arrayList.containsAll(mNewList) && arrayList.size == mNewList.size) return
        mDiffer?.submitList(mNewList, commitCallback)
    }

    override fun getItemId(position: Int): Long {
        if ((mDiffer?.currentList?.size ?: 0) < position) {
            return 0L
        }
        return mDiffer!!.currentList[position].hashCode().toLong()
    }


    private fun getMutablePageList(): ArrayList<T> {
        return ArrayList(mDiffer?.currentList ?: ArrayList())
    }

    private fun getAdapter(): BaseDiffPager2Adapter<T, L> {
        return this
    }

    override fun fragmentTag(pos: Int): String {
        return "f" + getItemId(pos)
    }
}