package com.yaxon.centralplainlion.ui.fragment;

import android.os.Bundle;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseLazyFragment;
import com.yaxon.centralplainlion.util.LogUtil;
import com.yaxon.centralplainlion.util.ToastUtil;

/**
 * Description:关注fragment
 * Created by kimiffy on 2020/1/14.
 */

public class FollowFragment extends BaseLazyFragment {

    public static FollowFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FollowFragment fragment = new FollowFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void onLazyLoad() {

    }
}
