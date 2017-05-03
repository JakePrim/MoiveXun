package com.moive.sus.library.base.core.retofit;

import android.widget.Toast;

import com.moive.sus.library.R;
import com.moive.sus.library.base.util.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by linksus on 5/2 0002.
 */

public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
//                                if (!Utils.isNetworkConnected()) {
//                                    Toast.makeText(context, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
//                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
