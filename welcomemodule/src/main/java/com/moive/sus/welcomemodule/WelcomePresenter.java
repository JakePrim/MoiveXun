package com.moive.sus.welcomemodule;

import android.content.Context;
import android.widget.Toast;

import com.moive.sus.library.base.api.DApi;
import com.moive.sus.library.base.core.retofit.BaseObserver;
import com.moive.sus.library.base.core.retofit.ExceptionHandle;
import com.moive.sus.library.base.core.retofit.RetrofitClient;
import com.moive.sus.library.base.util.LogUtils;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by linksus on 4/19 0019.
 * Class Note:
 * 处理欢迎页面逻辑类
 */

public class WelcomePresenter implements WelcomeContract.Presenter  {

    private Context mContext;
    private WelcomeContract.View mView;

    public WelcomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void initData() {
        RetrofitClient.getInstance(mContext, DApi.BASE_URL).createBaseApi().executePost(DApi.NOW_MOVIE_URL, new BaseObserver<ResponseBody>(mContext) {
            Disposable mDis;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mView.showLoadingView("");
                mDis = d;
            }

            @Override
            public void onNext(@NonNull ResponseBody o) {
                Toast.makeText(mContext, "onNext", Toast.LENGTH_LONG).show();
                try {

                    LogUtils.e(o.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {
                Toast.makeText(mContext, "onComplete", Toast.LENGTH_LONG).show();
                mView.showContentView();
                mDis.dispose();
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                mView.showErrorView("", "");
            }
        });

    }

    @Override
    public void attachView(WelcomeContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
