package com.gankao.crosssdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gankao.crosssdk.databinding.ActivityViewPager2Binding
import com.gankao.crosssdk.vp2.FragmentStateViewPager2Adapter
import com.gankao.crosssdk.vp2.fragment.BlankFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class ViewPager2Activity : AppCompatActivity() {

    private val mAdapter by lazy {
        FragmentStateViewPager2Adapter(this)
    }

    lateinit var binding: ActivityViewPager2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPager2Binding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        initData()

        binding.tvAdd.setOnClickListener {

            val count = mAdapter.itemCount
            mAdapter.addFragment(count, BlankFragment.newInstance("${count+1}","${count + 1}"), "${(count + 1)}")
            mAdapter.notifyDataSetChanged()
        }

        binding.tvRemove.setOnClickListener {
            val count = mAdapter.itemCount
            mAdapter.removeFragment(count - 1)
            mAdapter.notifyDataSetChanged()

        }
    }

    private fun initData() {


        binding.tabLayout.setTabMode(MODE_SCROLLABLE);//适合很多tab
        //tabLayoutUp.setTabMode(MODE_FIXED);//tab均分,适合少的tab
        binding.tabLayout.addOnTabSelectedListener(object :OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.vp2Main.adapter = mAdapter
        binding.vp2Main.offscreenPageLimit = 4

        TabLayoutMediator(binding.tabLayout, binding.vp2Main) { tab, position ->
            tab.text = mAdapter.getPageTitle(position)
        }.attach()

        refreshAdapter(true)

    }

    /**
     * 生成第一个页面
     */
    private fun refreshAdapter(isShow:Boolean ) {

        if (isShow) {
            // 动态加载选项卡内容
            mAdapter.addFragment(BlankFragment.newInstance("1","2"), "1")
            mAdapter.addFragment(BlankFragment.newInstance("2","2"), "2")
            mAdapter.addFragment(BlankFragment.newInstance("3","3"), "3")
            mAdapter.notifyDataSetChanged();
            binding.vp2Main.setCurrentItem(2, false)
        } else {
            mAdapter.clear();
        }
    }
}

