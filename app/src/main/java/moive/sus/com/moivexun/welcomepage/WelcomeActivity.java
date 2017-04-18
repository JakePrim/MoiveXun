package moive.sus.com.moivexun.welcomepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import moive.sus.com.moivexun.R;
import moive.sus.com.moivexun.base.AbsBaseActivity;

public class WelcomeActivity extends AbsBaseActivity implements WelcomeContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_welcome;
    }

    @Override
    public void toMainActivity() {

    }
}
