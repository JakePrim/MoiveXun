package moive.sus.com.moivexun.welcomepage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by linksus on 4/19 0019.
 * Class Note:
 * 处理欢迎页面逻辑类
 */

public class WelcomePresenter implements WelcomeContract.Presenter {

    private Context mContext;
    private WelcomeContract.View mView;

    public WelcomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void initData() {
        mView.showLoadingView("");
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mView.showNoNetworkView();
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void attachView(WelcomeContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
