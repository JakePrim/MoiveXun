package com.moive.sus.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.moive.sus.library.R;
import com.moive.sus.library.base.util.LogUtils;
import com.moive.sus.library.base.widget.MultipleStatusView;

/**
 * Created by 17604 on 2017/4/18.
 * Class Note:
 * all activities implement from this class
 */

public abstract class AbsBaseActivity extends AppCompatActivity implements BaseView {
    protected static String TAG_LOG = null;// Log tag

    protected Context mContext = null;//context
    public RelativeLayout content_view;
    public View activity_status_view;
    public MultipleStatusView statusView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mContext = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);
        if (getStatusLayoutView() != null) {
            setContentView(getStatusLayoutView());
        }
        initViewsAndEvents(savedInstanceState);
    }

    //https://github.com/qyxxjd/MultipleStatusView

    /**
     * setContentView
     * @return
     */
    protected View getStatusLayoutView() {
        activity_status_view = LayoutInflater.from(this).inflate(R.layout.lib_activity_show_status, null);
        statusView = (MultipleStatusView) activity_status_view.findViewById(R.id.main_multiplestatusview);
        content_view = (RelativeLayout) activity_status_view.findViewById(R.id.content_view);
        initListener();
        if (getContentViewID() != 0) {
            View contentView = LayoutInflater.from(this).inflate(getContentViewID(), null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            content_view.addView(contentView, params);
        }
        return activity_status_view;
    }

    /**
     * init views and events here
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    /**
     * bind layout resource file
     */
    protected abstract int getContentViewID();

    /**
     * 错误界面的点击事件处理
     */
    protected abstract void onRetryClick();

    /**
     * 初始化监听
     */
    protected void initListener() {
        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusView.showLoading();
                onRetryClick();
            }
        });
    }

    @Override
    public void showContentView() {
        statusView.showContent();
    }

    @Override
    public void showEmptyView() {
        statusView.showEmpty();
    }

    @Override
    public void showErrorView(String msg, String content) {
        statusView.showError();
    }

    @Override
    public void showLoadingView(String msg) {
        statusView.showLoading();
    }

    @Override
    public void showNoNetworkView() {
        statusView.showNoNetwork();
    }

    @Override
    public void close() { // 退出程序
        BaseAppManager.getInstance().AppExit(this);
    }

    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
