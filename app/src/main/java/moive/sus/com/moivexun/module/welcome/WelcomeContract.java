package moive.sus.com.moivexun.module.welcome;


import com.moive.sus.library.base.BasePresenter;
import com.moive.sus.library.base.BaseView;

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
