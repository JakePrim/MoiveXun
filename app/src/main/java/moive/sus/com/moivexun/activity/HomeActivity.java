package moive.sus.com.moivexun.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.moive.sus.library.base.AbsBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import moive.sus.com.moivexun.R;
import moive.sus.com.moivexun.module.home.HomeContract;
import moive.sus.com.moivexun.module.home.HomePresenter;

@Route(path = "/app/home")
public class HomeActivity extends AbsBaseActivity implements HomeContract.HomeView{


    private HomePresenter presenter;

    @BindView(R.id.frame_content)
    FrameLayout frame_content;

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

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
