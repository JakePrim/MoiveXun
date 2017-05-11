package com.moive.sus.library.base.core.retofit;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by linksus on 5/10 0010.
 *  Class Note:
 *  this is all Observer base class
 */

public abstract class BaseObserver<T> implements Observer<T> {

    private Context context;
    private Disposable d;

    public BaseObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        //todo loading
        this.d = d;
    }

    @Override
    public void onNext(@NonNull T t) {
        //todo 处理返回的结果
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("Tamic", e.getMessage());
        // todo error somthing
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
        d.dispose();
    }

    @Override
    public void onComplete() {
        //todo miss loading
        d.dispose();
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);
}