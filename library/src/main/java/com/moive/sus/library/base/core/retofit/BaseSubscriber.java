package com.moive.sus.library.base.core.retofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by linksus on 5/2 0002.
 */

public abstract class BaseSubscriber<T> implements Subscriber<T> {

    private Context context;
    private boolean isNeedCahe;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {
        Toast.makeText(context, "http is start", Toast.LENGTH_SHORT).show();

        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
//        if (!NetworkUtil.isNetworkAvailable(context)) {
//            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
//            onCompleted();
//        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("Tamic", e.getMessage());
        // todo error somthing

        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {
        Toast.makeText(context, "http is Complete", Toast.LENGTH_SHORT).show();
// todo some common as  dismiss loadding
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);
}
