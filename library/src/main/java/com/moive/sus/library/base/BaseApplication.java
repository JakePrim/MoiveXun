package com.moive.sus.library.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

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



    @Override
    public void onCreate() {

        super.onCreate();

        mHandler = new Handler();

        mContext = getApplicationContext();

//        mDataManager = new DataManager(getApplicationContext());

//Dagger2 inject
//        getAppComponent().inject(this);
//get DatabaseHelper instance
//        dbHelper = mDataManager.getDatabaseHelper();
//        mEventBus.register(this);
//        initAVOS();
//        initEaseUI(); // init EaseUI(for IM,Instant Messaging)
//        Thread.setDefaultUncaughtExceptionHandler(new LocalFileUncaughtExceptionHandler(this,
//                Thread.getDefaultUncaughtExceptionHandler()));   //exception handler
//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//        } else {
//            //oops,Fabric currently not available ,   Fabric.with(this, new Crashlytics());   Timber.plant(new CrashlyticsTree());
//            Timber.plant(new CrashReportingTree());
//        }

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
