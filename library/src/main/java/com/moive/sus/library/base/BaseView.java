package com.moive.sus.library.base;

/**
 * Created by linskSu on 2017/4/18.
 * Class Note:
 * interface for MVP View in all of the project
 * 显示loading 和 隐藏loading 、加载错误、网络错误、空数据
 */

public interface BaseView {
    void showLoadingView(String msg);//显示loading
    void showContentView();//隐藏loading
    void close();//关闭
    void showEmptyView();//空数据
    void showNoNetworkView();//网络错误
    void showErrorView(String msg, String content);//其他错误信息
}
