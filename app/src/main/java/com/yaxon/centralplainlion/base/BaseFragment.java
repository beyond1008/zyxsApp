package com.yaxon.centralplainlion.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yaxon.centralplainlion.http.callback.BaseObserver;
import com.yaxon.centralplainlion.util.StatusManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description: Fragment 基类
 * Created by kimiffy on 2019/2/13.
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 当前fragment绑定的Activity
     */
    protected AppCompatActivity mActivity;
    private Unbinder unbinder;
    protected View rootView;
    private CompositeDisposable compositeDisposable;//用于管理observer;
    /**
     * 获得全局的 Activity
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity)requireActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null ) {
            rootView = inflater.inflate(getLayoutResId(), null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initData(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initEventBus();
        setListener();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity=null;
    }

    /**
     * 获取界面布局id
     *
     * @return 该页面的layout
     */
    protected abstract int getLayoutResId();

    /**
     * 数据初始化
     *
     * @param savedInstanceState 保存的数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 界面初始化
     */
    protected abstract void initUI();

    /**
     * 设置监听
     */
    protected void setListener() {

    }


    /**
     * 是否使用eventbus 默认不使用  如果需要使用重写 返回true
     * @return
     */
    protected boolean isUseEventBus() {
        return false;
    }

    /**
     * 初始化EventBus
     */
    private void initEventBus() {
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 获取绑定的 Activity，防止出现 getActivity 为空
     */
    public AppCompatActivity getAttachActivity() {
        return mActivity;
    }


    /**
     * 新增disposable
     *
     * @param observable
     * @param observer
     */
    protected void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }


    /**
     * 移除disposable
     */
    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();//清空订阅,防止内存泄露
            compositeDisposable = null;
        }
    }



    private final StatusManager mStatusManager = new StatusManager();

    /**
     * 显示加载中
     */
    public void showLoading() {
        mStatusManager.showLoading(getAttachActivity());
    }

    public void showLoading(@StringRes int id) {
        mStatusManager.showLoading(getAttachActivity(), getString(id));
    }

    public void showLoading(CharSequence text) {
        mStatusManager.showLoading(getAttachActivity(), text);
    }

    /**
     * 显示加载完成
     */
    public void showComplete() {
        mStatusManager.showComplete();
    }

    /**
     * 显示空提示
     */
    public void showEmpty() {
        mStatusManager.showEmpty(getView());
    }

    /**
     * 显示错误提示
     */
    public void showError() {
        mStatusManager.showError(getView());
    }

    /**
     * 显示自定义提示
     */
    public void showStatusLayout(@DrawableRes int drawableId, @StringRes int stringId) {
        mStatusManager.showLayout(getView(), drawableId, stringId);
    }

    public void showStatusLayout(Drawable drawable, CharSequence hint) {
        mStatusManager.showLayout(getView(), drawable, hint);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        removeDisposable();
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 吐司
     */
    protected void showToast(String text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 页面跳转
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mActivity, clz));
    }

    /**
     * 页面跳转
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(mActivity, clz);
        startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz    要跳转的Activity
     * @param bundle 需要传递的数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 页面跳转并要求获取返回结果
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundle数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
