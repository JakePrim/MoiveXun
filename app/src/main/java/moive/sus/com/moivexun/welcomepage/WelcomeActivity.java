package moive.sus.com.moivexun.welcomepage;

import android.os.Bundle;

import com.moive.sus.library.base.AbsBaseActivity;

import moive.sus.com.moivexun.R;

public class WelcomeActivity extends AbsBaseActivity implements WelcomeContract.View {


    private WelcomePresenter presenter = new WelcomePresenter(this);

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter.attachView(this);
        presenter.initData();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_welcome;
    }

    @Override
    public void toMainActivity() {

    }

}
