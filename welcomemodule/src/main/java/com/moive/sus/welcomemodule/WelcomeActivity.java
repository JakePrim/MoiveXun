package com.moive.sus.welcomemodule;

import android.os.Bundle;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.moive.sus.library.base.AbsBaseActivity;

/**
 * Class Note:
 * this is welcome page
 */
@Route(path = "/module/welcome")
public class WelcomeActivity extends AbsBaseActivity implements WelcomeContract.View {

    private WelcomePresenter presenter;

   TextView mWelcomeTv;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mWelcomeTv = (TextView) findViewById(R.id.welcome_tv);
        presenter = new WelcomePresenter(this);
        presenter.attachView(this);
        presenter.initData();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.welcome_activity;
    }

    @Override
    protected void onRetryClick() {
        presenter.initData();
    }

    @Override
    public void toMainActivity() {
//        ARouter.getInstance().build("/app/home").navigation();
    }

    @Override
    public void startAdvter() {
        mWelcomeTv.setText("跳转到广告 - 准备跳转到MainActivity");
        presenter.endAdvter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
