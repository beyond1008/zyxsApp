package com.yaxon.centralplainlion.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Description:懒加载Fragment基类
 * 结合viewPager使用的fragment 可以实现懒加载
 * Created by kimiffy on 2019/9/29.
 */

public abstract class BaseLazyFragment extends BaseFragment {


    /**
     * fragment是否可见
     */
    private boolean isFragmentVisible;


    /**
     * 是否是第一次可见
     */
    private boolean isFirstVisible;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            if (isFirstVisible) {
                onLazyLoad();
                isFirstVisible = false;
            }
            onVisibleStateChange(true);
            isFragmentVisible = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onLazyLoad();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onVisibleStateChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onVisibleStateChange(false);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }



    /**
     * 保证只有当fragment可见状态发生变化时回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onVisibleStateChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可用于加载数据，防止每次进入都重复加载数据
     */
    protected abstract void onLazyLoad();


    /**
     * fragment 是否可见
     *
     * @return
     */
    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }


    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
    }


}
