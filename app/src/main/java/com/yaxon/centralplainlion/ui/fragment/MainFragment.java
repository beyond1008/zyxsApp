package com.yaxon.centralplainlion.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseFragment;
import com.yaxon.centralplainlion.ui.adapter.MainTabPagerAdapter;
import com.yaxon.centralplainlion.widget.badgeview.Badge;
import com.yaxon.centralplainlion.widget.badgeview.QBadgeView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:首页 容器fragment
 * Created by kimiffy on 2020/1/14.
 */

public class MainFragment extends BaseFragment {

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.iv_sign_in)
    ImageView mIvSignIn;
    @BindView(R.id.iv_news)
    ImageView mIvNews;
    @BindView(R.id.rl_news)
    RelativeLayout mRlNews;

    private List<Fragment> fragments;
    private List<String> tabList = Arrays.asList("首页", "热帖", "话题", "关注");

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fragments = new ArrayList<>();
    }

    @Override
    protected void initUI() {
        initFragments();
        initIndicator();

        // TODO: 2020/1/16 获取新消息 根据数据设置新消息数
        setNewMessage();
    }


    /**
     * 设置新消息数
     */
    private void setNewMessage() {
        Badge badge = new QBadgeView(getAttachActivity())
                .bindTarget(mRlNews)
                .setBadgeTextSize(25, false)
                .setBadgeGravity(Gravity.TOP | Gravity.END)
                .setGravityOffset(0, 5, true)
                .setBadgePadding(5, true);

        badge.setBadgeNumber(100);
    }

    /**
     * 初始化子fragment
     */
    private void initFragments() {
        HomeFragment homeFragment = HomeFragment.newInstance();//首页
        fragments.add(homeFragment);
        HotPostFragment hotPostFragment = HotPostFragment.newInstance();//热帖
        fragments.add(hotPostFragment);
        TopicFragment topicFragment = TopicFragment.newInstance();//话题
        fragments.add(topicFragment);
        FollowFragment followFragment = FollowFragment.newInstance();//关注
        fragments.add(followFragment);
        FragmentManager fm = getChildFragmentManager();
        MainTabPagerAdapter adapter = new MainTabPagerAdapter(fm, fragments, tabList);
        mVp.setAdapter(adapter);
    }

    /**
     * 初始化指示器
     */
    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabList == null ? 0 : tabList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(tabList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#fa513a"));
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVp.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));//线高
                indicator.setLineWidth(UIUtil.dip2px(context, 15));//线宽
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));//圆角大小
                indicator.setColors(Color.parseColor("#fa513a"));
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mVp);
    }


    @OnClick({R.id.iv_sign_in, R.id.iv_news})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_sign_in:
                showToast("签到!!");
                break;
            case R.id.iv_news:
                showToast("新消息!!");
                break;
        }
    }

}
