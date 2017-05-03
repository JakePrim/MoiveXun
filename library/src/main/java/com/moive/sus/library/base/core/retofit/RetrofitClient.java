package com.moive.sus.library.base.core.retofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linksus on 5/2 0002.
 */

public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT = 20;
    private ApiService apiService;
    private static OkHttpClient okHttpClient;
    public static String baseUrl;
    private static Context mContext;
    private static RetrofitClient sNewInstance;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl);
    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(
                mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }

        return new RetrofitClient(context, url);
    }

    public static RetrofitClient getInstance(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(context, url, headers);
    }

    private RetrofitClient() {

    }

    private RetrofitClient(Context context) {

        this(context, baseUrl, null);
    }

    private RetrofitClient(Context context, String url) {

        this(context, url, null);
    }

    private RetrofitClient(Context context, String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "tamic_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new CookieManger(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CaheInterceptor(context))
                .addNetworkInterceptor(new CaheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * ApiBaseUrl
     *
     * @param newApiBaseUrl
     */
    public static void changeApiBaseUrl(String newApiBaseUrl) {
        baseUrl = newApiBaseUrl;
        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl);
    }

    /**
     * addcookieJar
     */
    public static void addCookie() {
        okHttpClient.newBuilder().cookieJar(new CookieManger(mContext)).build();
        retrofit = builder.client(okHttpClient).build();
    }

    /**
     * ApiBaseUrl
     *
     * @param newApiHeaders
     */
    public static void changeApiHeader(Map<String, String> newApiHeaders) {

        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(newApiHeaders)).build();
        builder.client(httpClient.build()).build();
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        apiService = create(ApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

//    public Subscription getData(Subscriber<IpResult> subscriber, String ip) {
//        return apiService.getData(ip)
//                .compose(schedulersTransformer())
//                .compose(transformer())
//                .subscribe(subscriber);
//    }

//    public Subscription get(String url, Map parameters, Observer<BaseResponse<String>> observer) {
//
//        return apiService.executeGet(url, parameters).subscribe(observer);
//    }

    public void post(String url, Map<String, String> parameters, BaseSubscriber<ResponseBody> observer) {
        apiService.executePost(url, parameters)
                .compose(RxSchedulers.<ResponseBody>compose())
                .compose(transformer())
                .subscribe((Consumer<? super Object>) observer);
    }
//
//    public Subscription json(String url, RequestBody jsonStr, Subscriber<IpResult> subscriber) {
//
//        return apiService.json(url, jsonStr)
//                .compose(schedulersTransformer())
//                .compose(transformer())
//                .subscribe(subscriber);
//    }

//    public void upload(String url, RequestBody requestBody,Subscriber<ResponseBody> subscriber) {
//        apiService.upLoadFile(url, requestBody)
//                .compose(schedulersTransformer())
//                .compose(transformer())
//                .subscribe(subscriber);
//    }
//
//    public void download(String url, final CallBack callBack) {
//        apiService.downloadFile(url)
//                .compose(schedulersTransformer())
//                .compose(transformer())
//                .subscribe(new DownSubscriber<ResponseBody>(callBack));
//    }

//    Observable.Transformer schedulersTransformer() {
//        return new Observable.Transformer() {
//
//
//            @Override
//            public Object call(Object observable) {
//                return ((Observable) observable).subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
//
//    <T> Observable.Transformer<T, T> applySchedulers() {
//        return (Observable.Transformer<T, T>) schedulersTransformer();
//    }

    public <T> ObservableTransformer<T, T> transformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.map((Function<? super T, ? extends T>) new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    public <T> Observable<T> switchSchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }

    private class HandleFuc<T> implements Function<BaseResponse<T>, T> {

        @Override
        public T apply(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
            if (!tBaseResponse.isSuccess())
                throw new RuntimeException(tBaseResponse.getCode() + "" + tBaseResponse.getMessage() != null ? tBaseResponse.getMessage() : "");
            return tBaseResponse.getData();
        }
    }


    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return null;
    }


}
