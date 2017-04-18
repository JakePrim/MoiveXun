package moive.sus.com.moivexun.base;

/**
 * Created by linskSu on 2017/4/18.
 * Class Note:
 * interface for Presenter View in all of the project
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
