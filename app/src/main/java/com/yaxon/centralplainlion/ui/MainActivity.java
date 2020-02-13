package com.yaxon.centralplainlion.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseActivity;
import com.yaxon.centralplainlion.constant.Key;
import com.yaxon.centralplainlion.ui.fragment.MainFragment;
import com.yaxon.centralplainlion.ui.fragment.TestFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 主界面
 * Created by kimiffy on 2020/1/8.
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.btn_main_menu_tab1)
    Button mBtnMainMenuTab1;
    @BindView(R.id.btn_main_menu_tab2)
    Button mBtnMainMenuTab2;


    private final int TAB_SIZE = 4;
    @BindView(R.id.iv_main_menu_mic)
    ImageView mIvMainMenuMic;
    @BindView(R.id.btn_main_menu_tab3)
    Button mBtnMainMenuTab3;
    @BindView(R.id.btn_main_menu_tab4)
    Button mBtnMainMenuTab4;
    private Fragment[] mFragments = new Fragment[TAB_SIZE];
    private Button[] mTabs = new Button[TAB_SIZE];
    private String[] mFragmentTags = {"MainTag1", "MainTag2", "MainTag3", "MainTag4"};
    private int lastIndex;//记录上一次选中的下标

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //通过tag恢复保存的fragment  防止fragment重影
        if (savedInstanceState != null) {
            for (int i = 0; i < TAB_SIZE; i++) {
                Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, mFragmentTags[i]);
                if (null != fragment) {
                    mFragments[i] = fragment;
                }
            }
            lastIndex = savedInstanceState.getInt(Key.BUNDLE_STATE_LAST_INDEX, 0);
        }
        mTabs[0] = mBtnMainMenuTab1;
        mTabs[1] = mBtnMainMenuTab2;
        mTabs[2] = mBtnMainMenuTab3;
        mTabs[3] = mBtnMainMenuTab4;
    }

    @Override
    protected void initUI() {
        setSelectedTab(lastIndex);
    }


    @OnClick({R.id.btn_main_menu_tab1, R.id.btn_main_menu_tab2, R.id.iv_main_menu_mic, R.id.btn_main_menu_tab3, R.id.btn_main_menu_tab4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main_menu_tab1:
                setSelectedTab(0);
                break;
            case R.id.btn_main_menu_tab2:
                setSelectedTab(1);
                break;
            case R.id.iv_main_menu_mic:
                break;
            case R.id.btn_main_menu_tab3:
                setSelectedTab(2);
                break;
            case R.id.btn_main_menu_tab4:
                setSelectedTab(3);
                break;
        }
    }

    /**
     * 选择显示Fragment
     *
     * @param position 下标
     */
    private void setSelectedTab(int position) {
        mTabs[lastIndex].setSelected(false);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : mFragments) {
            if (null != fragment) {
                ft.hide(fragment);
            }
        }
        Fragment fragment = mFragments[position];
        if (null == fragment) {
            mFragments[position] = createFragment(position);
            ft.add(R.id.fl_content, mFragments[position]);
        } else {
            ft.show(fragment);
        }
        lastIndex = position;
        mTabs[lastIndex].setSelected(true);
        ft.commit();
    }

    /**
     * 实例化Fragment
     *
     * @param position 下标
     * @return fragment
     */
    private Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = MainFragment.newInstance();
                break;
            case 1:
                fragment = TestFragment.newInstance("求助");
                break;
            case 2:
                fragment = TestFragment.newInstance("发现");
                break;
            case 3:
                fragment = TestFragment.newInstance("我的");
                break;
        }
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //保存已经添加到FragmentManager的fragment
        for (int i = 0; i < TAB_SIZE; i++) {
            if (null != mFragments[i] && mFragments[i].isAdded()) {
                getSupportFragmentManager().putFragment(outState, mFragmentTags[i], mFragments[i]);
            }
        }
        outState.putInt(Key.BUNDLE_STATE_LAST_INDEX, lastIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments = null;
    }
}
