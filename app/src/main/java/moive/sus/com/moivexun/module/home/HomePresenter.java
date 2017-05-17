package moive.sus.com.moivexun.module.home;

import android.content.Context;

/**
 * Created by linksus on 5/17 0017.
 */

public class HomePresenter implements HomeContract.Presenter {

    private Context mContext;
    private HomeContract.HomeView mView;

    public HomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void attachView(HomeContract.HomeView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }


    @Override
    public void initData() {
        mView.showContentView();
        mView.initView();
    }
}
