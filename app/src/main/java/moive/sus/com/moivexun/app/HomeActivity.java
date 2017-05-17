package moive.sus.com.moivexun.app;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.moive.sus.library.base.AbsBaseActivity;
import com.moive.sus.library.base.interfaze.OnSwitchFragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import moive.sus.com.moivexun.R;
import moive.sus.com.moivexun.module.home.HomeContract;
import moive.sus.com.moivexun.module.home.HomePresenter;

@Route(path = "/app/home")
public class HomeActivity extends AbsBaseActivity implements HomeContract.HomeView, OnSwitchFragmentListener {


    private HomePresenter presenter;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        presenter = new HomePresenter(this);
        presenter.attachView(this);
        presenter.initData();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onRetryClick() {
        presenter.initData();
    }

    @Override
    public void finish() {
        super.finish();
        close();
    }

    @Override
    public void initView() {
        setOnSwitchFragmentListener(this);
    }

    @Override
    public void switchFragmentText(int switchs) {
    }
}
