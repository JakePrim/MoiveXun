package moive.sus.com.moivexun.module.welcome;

import android.content.Context;
import android.widget.Toast;

import com.moive.sus.library.base.core.retofit.BaseObserver;
import com.moive.sus.library.base.core.retofit.ExceptionHandle;
import com.moive.sus.library.base.core.retofit.RetrofitClient;
import com.moive.sus.library.base.core.retofit.theaterBean;
import com.moive.sus.library.base.util.LogUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import moive.sus.com.moivexun.api.DApi;
import okhttp3.ResponseBody;

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
        RetrofitClient.getInstance(mContext, DApi.BASE_URL).createBaseApi().postno(new BaseObserver<theaterBean>(mContext) {
            Disposable mDis;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mView.showLoadingView("");
                mDis = d;
            }

            @Override
            public void onNext(@NonNull theaterBean responseBody) {
                Toast.makeText(mContext, "onNext", Toast.LENGTH_LONG).show();
                LogUtils.e(responseBody.getTitle());
            }

            @Override
            public void onComplete() {
                Toast.makeText(mContext, "onComplete", Toast.LENGTH_LONG).show();
                mView.showContentView();
                mDis.dispose();
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
               mView.showErrorView("","");
            }
        });

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
