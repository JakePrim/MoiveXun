package moive.sus.com.moivexun;

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

public class MoiveApp extends BaseApplication {

    private static BaseApplication appContext;
    public static LogUtils.Builder lBuilder;
    public static BaseApplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        Utils.init(this);
        isDebug();
        logBuilder();
    }

    private void logBuilder() {
        lBuilder = new LogUtils.Builder()
                .setLogSwitch(BuildConfig.TAG_DEBUG)// 设置log总开关，默认开
                .setGlobalTag("Linksu")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
    }
    public void isDebug() {
        if (BuildConfig.TAG_DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }
}
