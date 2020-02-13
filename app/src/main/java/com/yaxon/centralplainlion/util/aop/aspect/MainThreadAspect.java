package com.yaxon.centralplainlion.util.aop.aspect;

import android.os.Looper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Description:主线程执行方法切面
 * Created by kimiffy on 2019/3/18.
 */

@Aspect
public class MainThreadAspect {

    @Pointcut("execution(@com.yaxon.centralplainlion.util.aop.annotation.MainThread * *(..))")
    public void methodAnnotated() {

    }

    @Around("methodAnnotated()")
    public void MainThreadPoint(final ProceedingJoinPoint point) throws Throwable {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            point.proceed();
        } else {
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    try {
                        point.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe();

        }
    }


}
