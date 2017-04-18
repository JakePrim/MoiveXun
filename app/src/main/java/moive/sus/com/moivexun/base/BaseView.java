package moive.sus.com.moivexun.base;

/**
 * Created by linskSu on 2017/4/18.
 * Class Note:
 * interface for MVP View in all of the project
 * 显示loading 和 隐藏loading 、加载错误、网络错误、空数据
 */

public interface BaseView {
    void showLoading(String msg);//显示loading
    void hideLoading();//隐藏loading
    void close();//关闭
    void emptyView();//空数据
    void netError();//网络错误
    void showErrorMessage(String msg,String content);//其他错误信息
}
