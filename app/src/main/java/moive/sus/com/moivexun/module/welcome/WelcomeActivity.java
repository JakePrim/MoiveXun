package moive.sus.com.moivexun.module.welcome;

import android.os.Bundle;

import com.moive.sus.library.base.AbsBaseActivity;

import moive.sus.com.moivexun.MainActivity;
import moive.sus.com.moivexun.R;

/**
 * Created by 17604 on 2017/4/18.
 * Class Note:
 * this is welcome page
 */
public class WelcomeActivity extends AbsBaseActivity implements WelcomeContract.View {


    private WelcomePresenter presenter;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter = new WelcomePresenter(this);
        presenter.attachView(this);
        presenter.initData();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onRetryClick() {
        presenter.initData();
    }

    @Override
    public void toMainActivity() {
        startActivity(MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
