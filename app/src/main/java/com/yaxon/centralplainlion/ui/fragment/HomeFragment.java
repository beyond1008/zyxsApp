package com.yaxon.centralplainlion.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseLazyFragment;
import com.yaxon.centralplainlion.bean.BannerBean;
import com.yaxon.centralplainlion.bean.HomeBean;
import com.yaxon.centralplainlion.bean.HomeFunctionBean;
import com.yaxon.centralplainlion.ui.adapter.HomeAdapter;
import com.yaxon.centralplainlion.ui.adapter.HomeFunctionAdapter;
import com.yaxon.centralplainlion.widget.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description: 首页fragment
 * Created by kimiffy on 2020/1/14.
 */

public class HomeFragment extends BaseLazyFragment implements PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.rlv_home)
    RecyclerView mRlvHome;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.float_btn_main)
    FloatingActionButton mFloatBtnMain;

    private List<HomeBean> homeList;
    private HomeAdapter mHomeAdapter;
    private LinearLayout mHeader;
    private Banner mBanner;
    private boolean mBannerIsReady = false;

    private RecyclerView mRlvHomeFunction;
    private HomeFunctionAdapter mHomeFunctionAdapter;
    private List<HomeFunctionBean> mFunctionList;
    private TextView mPostType;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        homeList = new ArrayList<>();
        mFunctionList = new ArrayList<>();
    }

    @Override
    protected void initUI() {
        //设置Header Footer为经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getAttachActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getAttachActivity()));

        mHomeAdapter = new HomeAdapter(mActivity, R.layout.item_rv_home, homeList);
        mRlvHome.setLayoutManager(new LinearLayoutManager(getAttachActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getAttachActivity(), DividerItemDecoration.VERTICAL);
        Drawable drawable = getResources().getDrawable(R.drawable.divider_recyclerview);//修改默认分割线颜色
        itemDecoration.setDrawable(drawable);
        mRlvHome.addItemDecoration(itemDecoration);

        mRlvHome.setAdapter(mHomeAdapter);
        mHomeAdapter.addHeaderView(getBannerView());//添加广告头部
        mHomeAdapter.addHeaderView(getFunctionView());//添加功能头部

        mHomeFunctionAdapter = new HomeFunctionAdapter(getAttachActivity(), R.layout.item_rlv_home_funtion, mFunctionList);
        mRlvHomeFunction.setLayoutManager(new GridLayoutManager(getAttachActivity(), 4));
        mRlvHomeFunction.setAdapter(mHomeFunctionAdapter);

    }

    @Override
    protected void onLazyLoad() {
        getBannerData();
        getFunctionData();
        getHomePostData();
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                mRefreshLayout.finishRefresh(1500);
                showEmpty();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishLoadMore(1500);
                showComplete();
            }
        });

        mHomeFunctionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeFunctionBean item = (HomeFunctionBean) adapter.getItem(position);
                if (null != item) {
                    showToast("点击 " + item.getTitle() + " 类型: " + item.getType());
                }
            }
        });

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showToast("点击第" + position + "个banner!");
            }
        });
    }


    @OnClick(R.id.float_btn_main)
    public void onViewClicked() {
        showToast("点击啦!");
    }

    /**
     * 创建Banner 头部
     *
     * @return
     */
    @SuppressLint("InflateParams")
    private View getBannerView() {
        mHeader = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_banner, null);
        mBanner = mHeader.findViewById(R.id.banner);
        return mHeader;
    }

    /**
     * 创建功能列表 头部
     *
     * @return
     */
    @SuppressLint("InflateParams")
    private View getFunctionView() {
        LinearLayout mHeader = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_home_function, null);
        mRlvHomeFunction = mHeader.findViewById(R.id.rlv_home_function);
        mPostType = mHeader.findViewById(R.id.tv_post_type);
        mPostType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu(v);
            }
        });
        return mHeader;
    }

    /**
     * 显示类型选择菜单
     */
    private void showPopMenu(View v) {
        PopupMenu popup = new PopupMenu(getAttachActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.home_post_type, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.post_type_all:
                mPostType.setText("全部");
                break;
            case R.id.post_type_1:
                mPostType.setText("卡友生活");
                break;
            case R.id.post_type_2:
                mPostType.setText("经验交流");
                break;
            case R.id.post_type_3:
                mPostType.setText("沿途风光");
                break;
            case R.id.post_type_4:
                mPostType.setText("曝光台");
                break;
            case R.id.post_type_5:
                mPostType.setText("卡友偶遇");
                break;
            case R.id.post_type_6:
                mPostType.setText("感谢帖");
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 获取首页帖子列表数据
     */
    private void getHomePostData() {

        // TODO: 2020/1/16 假数据
        for (int i = 0; i < 10; i++) {
            HomeBean homeBean = new HomeBean();
            homeList.add(homeBean);
        }
        mHomeAdapter.notifyDataSetChanged();

    }


    /**
     * 获取功能数据
     */
    private void getFunctionData() {
        // TODO: 2020/1/15 假数据
        mFunctionList.add(new HomeFunctionBean("卡友维权", 1, ""));
        mFunctionList.add(new HomeFunctionBean("货运物流", 2, ""));
        mFunctionList.add(new HomeFunctionBean("二手车门店", 3, ""));
        mFunctionList.add(new HomeFunctionBean("汽修联盟", 4, ""));
        mFunctionList.add(new HomeFunctionBean("善驾节油", 5, ""));
        mFunctionList.add(new HomeFunctionBean("联盟电台", 6, ""));
        mFunctionList.add(new HomeFunctionBean("能量豆商城", 7, ""));

    }

    /**
     * 获取广告数据
     */
    private void getBannerData() {
        // TODO: 2020/1/15 假数据
        List<BannerBean> beanList = new ArrayList<>();
        beanList.add(new BannerBean("https://www.wanandroid.com/blogimgs/e2a08fd9-e4d3-4fa0-b360-e811664f661a.png", "除了Bug，最让你头疼的问题是什么？"));
        beanList.add(new BannerBean("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png", "flutter 中文社区"));

        List<String> imageList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (BannerBean bannerBean : beanList) {
            imageList.add(bannerBean.getImagePath());
            titleList.add(bannerBean.getTitle());
        }
        if (!mActivity.isFinishing() && !mActivity.isDestroyed()) {
            mBanner.setImageLoader(new BannerImageLoader())
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setImages(imageList)
                    .setBannerAnimation(Transformer.Default)
                    .setBannerTitles(titleList)
                    .isAutoPlay(true)
                    .setDelayTime(10000)
                    .setIndicatorGravity(BannerConfig.RIGHT)
                    .start();
            mBannerIsReady = true;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBannerIsReady) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBannerIsReady) {
            mBanner.stopAutoPlay();
        }
    }


}
