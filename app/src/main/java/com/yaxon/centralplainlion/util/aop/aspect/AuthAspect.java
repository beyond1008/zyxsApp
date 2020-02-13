package com.yaxon.centralplainlion.util.aop.aspect;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yaxon.centralplainlion.util.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Description:认证切面
 * Created by kimiffy on 2019/9/30.
 */
@Aspect
public class AuthAspect {


    private static final String POINTCUT = "execution(@com.yaxon.centralplainlion.util.aop.annotation.NeedAuth * *(..))";

    @Pointcut(POINTCUT)
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void AuthPoint(final ProceedingJoinPoint point) throws Throwable {
        Object aThis = point.getThis();
        Context context = null;
        if (aThis instanceof Activity) {
            context = (Activity) aThis;
        } else if (aThis instanceof android.app.Fragment) {
            context = ((android.app.Fragment) aThis).getActivity();
        } else if (aThis instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) aThis).getActivity();
        }
        if (context == null) {
            LogUtil.e(" AuthAspect  context is null  !!");
            return;
        }
        //todo 这里需要替换判断用户是否登录 等信息
//        if (UserUtil.isUserLogin() && UserUtil.getUserInfo().getAuthentication() == 1) {
//            point.proceed();
//        } else {
//            showDialog(context);
//        }

    }

    /**
     * 显示一个dialog 提示认证
     */
    private void showDialog(Context context) {

        new AlertDialog.Builder(context)
                .setTitle("温馨提示")
                .setMessage("当前还未认证,是否前往认证?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("前往认证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // TODO: 2019/9/30 跳转认证页
                    }
                }).show();

    }
}
