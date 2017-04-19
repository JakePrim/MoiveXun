package moive.sus.com.moivexun.welcomepage;

import android.content.Context;

/**
 * Created by linksus on 4/19 0019.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {

    private Context mContext;
    private WelcomeContract.View mView;

    public WelcomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void initData() {
        mView.showEmptyView();
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
