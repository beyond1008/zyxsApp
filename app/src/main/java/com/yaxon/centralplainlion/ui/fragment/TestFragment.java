package com.yaxon.centralplainlion.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.base.BaseFragment;

import butterknife.BindView;

/**
 * Description:
 * Created by kimiffy on 2020/1/14.
 */

public class TestFragment extends BaseFragment {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    public static TestFragment newInstance(String content) {

        Bundle args = new Bundle();
        args.putString("test", content);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments) {
            String test = arguments.getString("test");
            mTvTest.setText(test);
        }
    }

    @Override
    protected void initUI() {

    }

}
