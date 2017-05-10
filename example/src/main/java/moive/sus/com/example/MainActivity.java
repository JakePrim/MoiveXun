package moive.sus.com.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.moive.sus.library.base.util.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "rxjava";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
//        initRxJava();
//        cosumer();
//        mainNewThread();
//        rxNetwork();
//        rxMap();
        rxFlatMap();
    }

    //基础写法
    private void initRxJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(@NonNull Disposable d) {//先 调用
                LogUtils.v("rxjava", "onSubscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.v("rxjava", "onNext:" + integer);
                i++;
                if (i == 2) {
                    mDisposable.dispose();//切断水管 但是上游会继续发送剩余的事件,下游不会在接收事件.
                    LogUtils.v("rxjava", "isDisposed:" + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.v("rxjava", "onError");
            }

            @Override
            public void onComplete() {
                LogUtils.v("rxjava", "onComplete");
            }
        });
    }

    /**
     *   public final Disposable subscribe() {}
     *   不带任何参数的subscribe() 表示下游不关心任何事件,你上游尽管发你的数据去吧, 老子可不管你发什么.
     */


    /**
     * 下游的其他重载方法
     * public final Disposable subscribe(Consumer<? super T> onNext) {}
     * 带有一个Consumer参数的方法表示下游只关心onNext事件, 其他的事件我假装没看见, 因此我们如果只需要onNext事件可以这么写:
     * 同理
     * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {} 只关心onNext onError事件
     * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {} 只关心onNext onError onComplete事件
     * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {}
     */
    private void cosumer() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
                e.onNext(4);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.v("rxjava", "onNext:" + integer);
                textView.setText("onNext:" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.v("rxjava", "onError:");
            }
        });

    }


    /**
     * 上游和下游可以在不同的线程中执行
     * 上游发送事件的线程, 让它去子线程中发送事件, 然后再改变下游的线程, 让它去主线程接收事件
     */
    private void mainNewThread() {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
//                LogUtils.v("rxjava", "Observable thread is" + Thread.currentThread().getName());
                Log.d("rxjava", "Observable thread is" + Thread.currentThread().getName());
                e.onNext(1);
                e.onNext(2);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.d("rxjava", "Observable thread is" + Thread.currentThread().getName());
                LogUtils.d("rxjava", "Consumer thread is" + Thread.currentThread().getName());
                LogUtils.d("rxjava", "onNext:" + integer);
                textView.setText("onNext:" + integer + "Consumer thread is" + Thread.currentThread().getName());
            }
        };

        observable.subscribeOn(Schedulers.newThread());//上游在子线程中执行 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
        observable.observeOn(AndroidSchedulers.mainThread());//下游在主线程中执行
        observable.observeOn(Schedulers.io());//多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
        observable.subscribe(consumer);
    }

    /**
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
     * Schedulers.newThread() 代表一个常规的新线程
     * AndroidSchedulers.mainThread() 代表Android的主线程
     */
    private void rxNetwork() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(2);
            }
        }); //上游


        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.setText("onNext:" + integer + "Consumer thread is" + Thread.currentThread().getName());
            }
        };

        observable.subscribeOn(Schedulers.newThread())//在一个新的线程中执行
                .subscribeOn(Schedulers.io())//在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() { //doOnNext 在切换线程时的处理
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
                    }
                })
                .subscribe(consumer);
    }


    /**
     * RxJava 中的操作符
     * map是RxJava中最简单的一个变换操作符了, 它的作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化.
     */
    private void rxMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "This is result:" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                textView.setText(s);
            }
        });
    }

    /**
     * flatMap是一个非常强大的操作符, 先用一个比较难懂的概念说明一下:
     * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里.
     */
    private void rxFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am Value:"+integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                textView.setText(o.toString());
            }
        });

    }

    /***
     * concatMap
     * 它和flatMap的作用几乎一模一样, 只是它的结果是严格按照上游发送的顺序来发送的
     */
    private void rxconcatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am Value:"+integer);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                textView.setText(o.toString());
            }
        });
    }
}
