package com.yaxon.centralplainlion.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.bean.HomeBean;
import com.yaxon.centralplainlion.util.imageloader.ImageLoader;

import java.util.List;

/**
 * Description:
 * Created by kimiffy on 2020/1/15.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeBean,BaseViewHolder> {
    private Context mContext;
    public HomeAdapter(Context context, int layoutResId, @Nullable List<HomeBean> data) {
        super(layoutResId, data);
        this.mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeBean item) {

        ImageView head = helper.getView(R.id.iv_head);
        ImageView picture = helper.getView(R.id.iv_picture);
        ImageLoader.LoadCircleImage(mContext,"http://img.zcool.cn/community/011d8d58a93fe1a801219c7783dae0.png@2o.png",head);
        ImageLoader.LoadImage(mContext,"http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1701/12/c0/35300356_1484151185322_mthumb.jpg",picture);
    }
}
