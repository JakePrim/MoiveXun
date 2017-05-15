package moive.sus.com.moivexun.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.moive.sus.library.base.AbsBaseActivity;

import moive.sus.com.moivexun.R;

@Route(path = "/app/home")
public class HomeActivity extends AbsBaseActivity {

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onRetryClick() {

    }

    @Override
    public void finish() {
        super.finish();
        close();
    }
}
