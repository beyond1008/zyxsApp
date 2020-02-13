package com.yaxon.centralplainlion.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.bean.HomeFunctionBean;
import com.yaxon.centralplainlion.util.imageloader.ImageLoader;

import java.util.List;

/**
 * Description:
 * Created by kimiffy on 2020/1/15.
 */

public class HomeFunctionAdapter  extends BaseQuickAdapter<HomeFunctionBean,BaseViewHolder> {

    private Context mContext;

    public HomeFunctionAdapter(Context context, int layoutResId, @Nullable List<HomeFunctionBean> data) {
        super(layoutResId, data);
        this.mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeFunctionBean item) {
        helper.setText(R.id.tv_home_function_title,item.getTitle());
        ImageView icon = helper.getView(R.id.iv_home_function_icon);
        ImageLoader.LoadCircleImage(mContext,"https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=a76e3a8a47540923aa696478aa63b634/f3d3572c11dfa9eca0cc92be6cd0f703908fc1bd.jpg",icon);
    }
}