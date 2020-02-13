package com.yaxon.centralplainlion.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yaxon.centralplainlion.R;
import com.yaxon.centralplainlion.http.callback.BaseObserver;
import com.yaxon.centralplainlion.util.StatusManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Description: activity基类
 * Created by kimiffy on 2019/2/13.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable;//用于管理observer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);
        initData(savedInstanceState);
        initUI();
        initHeaderTitle();
        initEventBus();
        setListener();
    }

    /**
     * 获取界面布局id
     *
     * @return 该页面的layout
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 数据初始化
     *
     * @param savedInstanceState 保存的状态
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 视图初始化
     */
    protected abstract void initUI();


    /**
     * 设置监听
     */
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 是否使用eventbus 默认不使用  如果需要使用重写 返回true
     * @return  是否使用
     */
    protected boolean isUseEventBus() {
        return false;
    }


    /**
     * 初始化EventBus,检测是否有@BindEventBus注解,有注解则注册
     */
    private void initEventBus() {
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 获取当前 Activity 对象
     */
    public BaseActivity getActivity() {
        return this;
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
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
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(observer));
        }
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
        mStatusManager.showLoading(this);
    }

    public void showLoading(@StringRes int id) {
        mStatusManager.showLoading(this, getString(id));
    }

    public void showLoading(CharSequence text) {
        mStatusManager.showLoading(this, text);
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
        mStatusManager.showEmpty(getContentView());
    }

    /**
     * 显示错误提示
     */
    public void showError() {
        mStatusManager.showError(getContentView());
    }

    /**
     * 显示自定义提示
     */
    public void showStatusLayout(@DrawableRes int drawableId, @StringRes int stringId) {
        mStatusManager.showLayout(getContentView(), drawableId, stringId);
    }

    public void showStatusLayout(Drawable drawable, CharSequence hint) {
        mStatusManager.showLayout(getContentView(), drawable, hint);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        removeDisposable();
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    //---------------------------------------公共方法-----------------------------------------------

    /**
     * 吐司
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 页面跳转
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 页面跳转
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
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
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 页面跳转并要求获取返回结果
     *
     * @param clz         要跳转的Activity
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        startActivityForResult(intent, requestCode);
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
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 初始化通用的头部标题(如果使用的是自己写的头部布局请忽略)
     * 头部右侧按钮不够通用,这里不做统一处理 ,子类需要操作右侧按钮重写 getHeaderRightButton即可
     */
    private void initHeaderTitle() {
        String title = getHeaderTitle();
        if ("no_title".equals(title)) {
            return;
        }
        TextView titleTv = findViewById(R.id.title_content_text);
        Button leftIcon = findViewById(R.id.button_left);
        Button rightButton = findViewById(R.id.button_right);
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTv.setText(title);
        if (hideHeaderBack()) {
            leftIcon.setVisibility(View.GONE);
        }
        getHeaderRightButton(rightButton);
    }

    /**
     * 获取页面通用头部标题,不是采用通用头部的 不用重写
     * 不强制重写 如有需要复写即可
     * 默认返回"no_title",如果是"no_title" 需要具体实现类自己去处理 返回 标题等操作
     *
     * @return 该页面的头部标题
     */
    protected String getHeaderTitle() {
        return "no_title";
    }


    /**
     * 通用头部右侧按钮 子类可重写 对rightbutton 操作
     * 默认是 不可见的 需 rightButton.setVisibility(View.VISIBLE);
     * @param rightButton
     */
    protected void getHeaderRightButton(Button rightButton) {}

    /**
     * 是否隐藏通用头部左侧返回按钮
     *
     * @return
     */
    protected boolean hideHeaderBack() {
        return false;
    }
}
