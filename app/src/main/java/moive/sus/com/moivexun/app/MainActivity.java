package moive.sus.com.moivexun.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.moive.sus.library.base.AbsBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moive.sus.com.moivexun.R;

public class MainActivity extends AbsBaseActivity {

    @BindView(R.id.tv_main)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ARouter.init(getApplication());
        ARouter.getInstance().build("/module/welcome").navigation();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onRetryClick() {

    }

    @OnClick(R.id.tv_main)
    public void main() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ARouter.getInstance().destroy();
    }
}
