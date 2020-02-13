package com.yaxon.centralplainlion.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:下拉时显示FAB  上拉隐藏 FAB
 * Created by kimiffy on 2020/1/16.
 */
public class ScaleDownShowBehavior extends FloatingActionButton.Behavior {

    /**
     * 退出动画是否正在执行。
     */
    private boolean isAnimatingOut = false;

    private static final LinearOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();

    public ScaleDownShowBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimatingOut && child.getVisibility() == View.VISIBLE) {//往下滑
            scaleHide(child, viewPropertyAnimatorListener);
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.getVisibility() != View.VISIBLE) {
            scaleShow(child);
        }
    }

    private ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {

        @Override
        public void onAnimationStart(View view) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimatingOut = false;
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(View arg0) {
            isAnimatingOut = false;
        }
    };


    // 显示view
    private void scaleShow(View view) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view)
                .scaleX(1.0F)
                .scaleY(1.0F)
                .alpha(1.0F)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .withLayer()
                .setListener(null)
                .start();
    }

    // 隐藏view
    private void scaleHide(final View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {

        ViewCompat.animate(view)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .alpha(0.0f)
                .withLayer()
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }
}
