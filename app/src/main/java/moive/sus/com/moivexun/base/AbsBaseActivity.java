package moive.sus.com.moivexun.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moive.sus.com.moivexun.R;

/**
 * Created by 17604 on 2017/4/18.
 */

public abstract class AbsBaseActivity extends AppCompatActivity implements BaseView{
    protected static String TAG_LOG = null;// Log tag

    protected Context mContext = null;//context
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState){
        mContext = this;
//        if (getStatusLayoutView() != null) {
//            setContentView(getStatusLayoutView());
//        }
        initViewsAndEvents(savedInstanceState);
    }

    //https://github.com/qyxxjd/MultipleStatusView
//    protected View getStatusLayoutView() {
////        mStatusLayout = (StatusLayout) LayoutInflater.from(this).inflate(R.layout.lib_activity_show_status, null);
////        if (getContentViewID() != 0) {
//////            FrameLayout statusFrameLayout = (FrameLayout) findViewById(R.id.status_frame_content);
////            View contentView = LayoutInflater.from(this).inflate(getContentViewID(), null);
////            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////            mStatusLayout.addView(contentView, params);
////        }
////        return mStatusLayout;
//    }
    /**
     * init views and events here
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    /**
     * bind layout resource file
     */
    protected abstract int getContentViewID();
    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void close() {

    }

    @Override
    public void emptyView() {

    }

    @Override
    public void netError() {

    }

    @Override
    public void showErrorMessage(String msg, String content) {

    }
    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
