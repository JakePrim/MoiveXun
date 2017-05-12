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

    private static Handler mHandler;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mContext = getApplicationContext();
    }


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
