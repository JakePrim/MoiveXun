package com.moive.sus.welcomemodule;

import android.content.Context;
import android.os.Handler;
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

public class WelcomePresenter implements WelcomeContract.Presenter {

    private Context mContext;
    private WelcomeContract.View mView;

    public WelcomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void initData() {
        mView.startAdvter();
    }

    @Override
    public void endAdvter() {//广告结束 准备跳转到MainActivity
        mView.toMainActivity();
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
