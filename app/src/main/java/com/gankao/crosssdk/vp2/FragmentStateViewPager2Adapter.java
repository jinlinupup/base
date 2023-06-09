package com.gankao.crosssdk.vp2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 13:53
 */
public class FragmentStateViewPager2Adapter extends FragmentStateAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    private List<String> mTitleList = new ArrayList<>();

    private List<Long> mIds = new ArrayList<>();

    private AtomicLong mAtomicLong = new AtomicLong(0);


    public FragmentStateViewPager2Adapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    public FragmentStateViewPager2Adapter addFragment(Fragment fragment, String title) {
        if (fragment != null) {
            mFragmentList.add(fragment);
            mTitleList.add(title);
            mIds.add(getAtomicGeneratedId());
        }
        return this;
    }

    /**
     * 添加
     */
    public FragmentStateViewPager2Adapter addFragment(int index, Fragment fragment, String title) {
        if (fragment != null && index >= 0 && index <= mFragmentList.size()) {
            mFragmentList.add(index, fragment);
            mTitleList.add(index, title);
            mIds.add(index, getAtomicGeneratedId());
        }
        return this;
    }


    /**
     * 删除
     */
    public FragmentStateViewPager2Adapter removeFragment(int index) {
        if (index >= 0 && index < mFragmentList.size()) {
            mFragmentList.remove(index);
            mTitleList.remove(index);
            mIds.remove(index);
        }
        return this;
    }

    /**
     * 删除所有
     */
    public FragmentStateViewPager2Adapter removeFragmentAll() {
        mFragmentList.clear();
        mTitleList.clear();
        mIds.clear();
        return this;
    }

    private long getAtomicGeneratedId() {
        return mAtomicLong.incrementAndGet();
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    public void clear() {
        mFragmentList.clear();
        mTitleList.clear();
        mIds.clear();
        notifyDataSetChanged();
    }

    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mIds.get(position);
    }

    @Override
    public boolean containsItem(long itemId) {
        return mIds.contains(itemId);
    }
}