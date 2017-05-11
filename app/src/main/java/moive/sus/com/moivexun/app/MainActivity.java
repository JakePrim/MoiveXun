package moive.sus.com.moivexun.app;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.moive.sus.library.base.AbsBaseActivity;

import moive.sus.com.moivexun.R;

public class MainActivity extends AbsBaseActivity {

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        ARouter.init(getApplication());
        ARouter.getInstance()
                .build("/app/home")
                .navigation();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onRetryClick() {

    }
}
