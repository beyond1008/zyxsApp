package com.yaxon.centralplainlion.ui.fragment;

import android.os.Bundle;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseLazyFragment;

/**
 * Description:热帖 fragment
 * Created by kimiffy on 2020/1/14.
 */

public class HotPostFragment extends BaseLazyFragment {

    public static HotPostFragment newInstance() {

        Bundle args = new Bundle();

        HotPostFragment fragment = new HotPostFragment();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_hot_post;
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
