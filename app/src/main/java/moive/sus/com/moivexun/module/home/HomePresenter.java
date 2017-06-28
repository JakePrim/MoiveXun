package moive.sus.com.moivexun.module.home;

import android.content.Context;

import com.moive.sus.library.base.api.DApi;
import com.moive.sus.library.base.core.retofit.BaseObserver;
import com.moive.sus.library.base.core.retofit.ExceptionHandle;
import com.moive.sus.library.base.core.retofit.RetrofitClient;
import com.moive.sus.library.base.util.LogUtils;

import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;

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
        RetrofitClient.getInstance(mContext).executePost(DApi.NOW_MOVIE_URL, new BaseObserver<ResponseBody>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                mView.showErrorView("", "");
            }

            @Override
            public void onQuestComplete() {
                mView.showContentView();
                mView.initView();
            }

            @Override
            public void onQuestResult(@NonNull ResponseBody responseBody) {
                LogUtils.v(responseBody.source().toString());
            }


            @Override
            public void onStartQuest() {
                mView.showLoadingView("");
            }
        });

    }
}
