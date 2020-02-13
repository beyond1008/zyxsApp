package com.yaxon.centralplainlion.ui.fragment;

import android.os.Bundle;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseLazyFragment;

/**
 * Description:话题fragment
 * Created by kimiffy on 2020/1/14.
 */

public class TopicFragment extends BaseLazyFragment {

    public static TopicFragment newInstance() {

        Bundle args = new Bundle();

        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_topic;
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
