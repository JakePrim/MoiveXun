package moive.sus.com.moivexun.module.home;

import com.moive.sus.library.base.BasePresenter;
import com.moive.sus.library.base.BaseView;

/**
 * Created by linskSu on 2017/4/18.
 * Class Note:
 * contract class for home view & presenter
 */

public class HomeContract {
    public interface Presenter extends BasePresenter<HomeContract.HomeView> {
        void initData();
    }

    public interface HomeView extends BaseView {
        void initView();
    }
}
