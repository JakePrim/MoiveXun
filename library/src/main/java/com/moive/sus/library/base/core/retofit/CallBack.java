package com.moive.sus.library.base.core.retofit;

/**
 * Created by linksus on 5/2 0002.
 * 所有Api的处理 ，列如无网络情况，统一显示Progress
 */

public abstract class CallBack {
    public void onStart(){}

    public void onCompleted(){}

    abstract public void onError(Throwable e);

    public void onProgress(long fileSizeDownloaded){}

    abstract public void onSucess(String path, String name, long fileSize);
}
