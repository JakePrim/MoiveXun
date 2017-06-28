package moive.sus.com.moivexun;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.moive.sus.library.base.BaseApplication;
import com.moive.sus.library.base.util.LogUtils;
import com.moive.sus.library.base.util.Utils;

/**
 * Created by linksus on 4/20 0020.
 * Class Note:
 * Application
 * use in AndroidManifest.xml
 */

public class MoiveApp extends Application {

    private static Application appContext;

    public static Application getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        isDebug();
    }

    public void isDebug() {
        if (BuildConfig.TAG_DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }
}
