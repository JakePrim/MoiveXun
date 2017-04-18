package moive.sus.com.moivexun.welcomepage;

import android.view.View;

import moive.sus.com.moivexun.base.BasePresenter;
import moive.sus.com.moivexun.base.BaseView;

/**
 * Created by linskSu on 2017/4/18.
 * Class Note:
 * contract class for splash view & presenter
 */

public class WelcomeContract {
    interface Presenter extends BasePresenter<View> {
        void initData();
    }

    interface View extends BaseView {
        void toMainActivity();
    }
}
