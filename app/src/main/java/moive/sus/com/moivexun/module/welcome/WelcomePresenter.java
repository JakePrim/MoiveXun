package moive.sus.com.moivexun.module.welcome;

import android.content.Context;

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
        mView.showNoNetworkView();
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
//                mView.showLoadingView("");
//                e.onNext(1);
//            }
//        }).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(@NonNull Object o) throws Exception {
//                mView.showNoNetworkView();
//            }
//        });
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
