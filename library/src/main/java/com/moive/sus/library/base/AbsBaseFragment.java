package com.moive.sus.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.moive.sus.library.R;
import com.moive.sus.library.base.widget.MultipleStatusView;

/**
 * Created by linksus on 5/18 0018.
 * Class Note:
 * <p/>
 * Base Fragment for all the Fragment defined in the project
 * 1 extended from {@link AbsBaseFragment} to do
 * some base operation.
 * 2 do operation in {@link #initViews(View, Bundle)}
 */

public abstract class AbsBaseFragment extends Fragment implements BaseView {
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;
    /**
     * url and title passed into fragment
     */
    public static String EXTRA_URL = "url";
    private String mUrl;
    public static String EXTRA_TITLE = "url";
    private String mTitle;
    /**
     * activity context of fragment
     */
    protected Context mContext;
    protected Activity mActivity;
    public RelativeLayout content_view;
    public View activity_status_view;
    public MultipleStatusView statusView;

    @Override
    public void onAttach(Context context) {
        //set a context from current activity
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
        if (getArguments() != null) {
            mUrl = getArguments().getString(EXTRA_URL);
            mTitle = getArguments().getString(EXTRA_TITLE);
        }
        loadData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        }
        if (getLayoutId() != 0) {
            return inflater.inflate(getLayoutId(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //设置状态栏透明
//        setStatusBarColor();
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public String getFragmentUrl() {
        return mUrl;
    }

    public void setFragmentUrl(String url) {
        this.mUrl = url;
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method.
     * We have added this method to avoid duplicate all the inflate code in every fragment.
     * You only have to return the layout to inflate in this method when extends AbsBaseFragment.
     */
    protected abstract int getLayoutId();

    public View getLayoutView() {
        activity_status_view = LayoutInflater.from(mContext).inflate(R.layout.lib_activity_show_status, null);
        statusView = (MultipleStatusView) activity_status_view.findViewById(R.id.main_multiplestatusview);
        content_view = (RelativeLayout) activity_status_view.findViewById(R.id.content_view);
        initListener();
        if (getLayoutId() != 0) {
            View contentView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            content_view.addView(contentView, params);
        }
        return activity_status_view;
    }

    /**
     * 错误等界面的点击事件处理
     */
    protected abstract void onRetryClick();

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
    public void close() { // 退出fragment
        getFragmentManager().popBackStack();
    }

    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViews(View rootView, Bundle savedInstanceState);

    protected abstract void loadData();//load data in onCreate method

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
//        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
//               toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }


}
