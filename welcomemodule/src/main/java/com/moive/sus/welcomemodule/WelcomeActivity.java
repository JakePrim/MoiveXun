package com.moive.sus.welcomemodule;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.moive.sus.library.base.AbsBaseActivity;
import com.moive.sus.library.base.util.LogUtils;

import net.youmi.android.AdManager;
import net.youmi.android.normal.common.ErrorCode;
import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

/**
 * Class Note:
 * this is welcome page
 */
@Route(path = "/module/welcome")
public class WelcomeActivity extends AbsBaseActivity implements WelcomeContract.View {

    private static final String TAG = "welcome";
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
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.welcome_activity;
    }

    @Override
    protected void onRetryClick() {
        presenter.initData();
    }

    @Override
    public void toMainActivity() {
        ARouter.getInstance().build("/app/home").navigation();
    }

    @Override
    public void startAdvter() {
        setupSplashAd();
    }

    private void setupSplashAd() {
        AdManager.getInstance(this).init(Constant.YM_ID, Constant.YM_KEY, true);
        // 创建开屏容器
        RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.rl_splash);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.view_divider);
        // 对开屏进行设置
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        // 设置跳转的窗口类
        //splashViewSettings.setTargetClass(MainActivity.class);
        // 设置开屏的容器
        splashViewSettings.setSplashViewContainer(splashLayout);
        // 展示开屏广告
        SpotManager.getInstance(mContext).showSplash(mContext, splashViewSettings, new SpotListener() {

            @Override
            public void onShowSuccess() {
                Log.v(TAG, "开屏展示成功");
            }

            @Override
            public void onShowFailed(int errorCode) {
                LogUtils.v("开屏展示失败");
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        Log.v(TAG, "网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        Log.v(TAG, "暂无开屏广告");
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        Log.v(TAG, "开屏资源还没准备好");
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        Log.v(TAG, "开屏展示间隔限制");
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        Log.v(TAG, "开屏控件处在不可见状态");
                        break;
                    default:
                        Log.v(TAG, "errorCode: %d" + errorCode);
                        break;
                }
            }

            @Override
            public void onSpotClosed() {
                Log.v(TAG, "开屏被关闭");
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {
                Log.v(TAG, "开屏被点击");
                Log.v(TAG, "是否是网页广告？%s" + isWebPage);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        SpotManager.getInstance(this).onDestroy();
    }
}
