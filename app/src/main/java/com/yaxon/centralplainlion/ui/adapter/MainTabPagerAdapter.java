package com.yaxon.centralplainlion.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description:首页 fragment adapter
 * Created by kimiffy on 2020/1/14.
 */

public class MainTabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public MainTabPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabTitles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null && position < mFragments.size()) {
            return mFragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mFragments == null) {
            return 0;
        }
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position < mTitles.size() ? mTitles.get(position) : null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        //空实现,注释父类的方法,不让销毁视图
    }
}
