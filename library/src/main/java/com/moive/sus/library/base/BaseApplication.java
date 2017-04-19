package com.moive.sus.library.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.moive.sus.library.BuildConfig;
import com.moive.sus.library.base.util.LogUtils;
import com.moive.sus.library.base.util.Utils;

/**
 * Created by linksus on 4/19 0019.
 * Class Note:
 * Base Application for Application
 * use in AndroidManifest.xml
 */

public class BaseApplication extends Application {


    //    DataManager mDataManager;
    private static Handler mHandler;
    private static Context mContext;
    public static LogUtils.Builder lBuilder;
    private static BaseApplication appContext;

    public static BaseApplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        appContext = this;
        Utils.init(this);
        lBuilder = new LogUtils.Builder()
//                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
                .setGlobalTag("Linksu")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
        mHandler = new Handler();
        mContext = getApplicationContext();
    }


//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);//multi dex support
//    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHanlder() {
        return mHandler;
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

}
