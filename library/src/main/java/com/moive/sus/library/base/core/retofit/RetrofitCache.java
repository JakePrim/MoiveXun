package com.moive.sus.library.base.core.retofit;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by linksus on 5/2 0002.
 * 处理数据缓存
 */

public class RetrofitCache {
    public static <T> Observable<T> load(final String cacheKey, Observable<T> fromNetwork, boolean isSave, boolean forceRefresh) {
        Observable<T> fromCache = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
//                T cache = (T) Hawk.get(cacheKey);
//                if (cache != null) {
//                    e.onNext(cache);
//                } else {
//                    e.onComplete();
//                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        //是否缓存
        if (isSave) {
            /**
             * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
             */
            fromNetwork = fromNetwork.map(new Function<T, T>() {
                @Override
                public T apply(@NonNull T t) throws Exception {
//                    Hawk.put(cacheKey, t);
                    return t;
                }
            });
        }
        //强制刷新
        if (forceRefresh) {
            return fromNetwork;
        } else {
            return fromNetwork;
//            return Observable.concat(fromCache, fromNetwork).first(T);
        }
    }
}
