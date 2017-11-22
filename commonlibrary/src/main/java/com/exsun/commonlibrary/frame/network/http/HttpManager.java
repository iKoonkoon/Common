package com.exsun.commonlibrary.frame.network.http;

import com.exsun.commonlibrary.base.BaseApi;
import com.exsun.commonlibrary.frame.network.exception.ExceptionFunc;
import com.exsun.commonlibrary.frame.network.exception.RetryWhenNetworkException;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextSubListener;
import com.exsun.commonlibrary.frame.network.result.ResulteFunc;
import com.exsun.commonlibrary.frame.network.subscriber.ProgressSubscriber;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.BuildConfig;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * HTTP 交互
 * <p>
 * Created by MrKong on 2017/9/12.
 */

public class HttpManager
{
    private SoftReference<HttpOnNextListener> onNextListener;
    private SoftReference<HttpOnNextSubListener> onNextSubListener;
    private SoftReference<RxAppCompatActivity> appCompatActivity;
    
    public HttpManager(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        this.onNextListener = new SoftReference(onNextListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }
    
    public HttpManager(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        this.onNextSubListener = new SoftReference(onNextSubListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }
    
    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
//    public void doHttpDeal(Observable observable, BaseApi basePar)
//    {
//        httpDeal(observable, basePar);
//    }
    
    /**
     * 获取Retrofit对象
     *
     * @param connectTime
     * @param baseUrl
     * @return
     */
    public Retrofit getRetrofit(String baseUrl, int connectTime, Interceptor headerInterceptor)
    {
        // 初始化 OkHttpClient
        OkHttpClient okHttpClient = initOkHttpClient(connectTime, headerInterceptor);
        // 初始化 Retrofit
        Retrofit retrofit = initRetrofit(okHttpClient, baseUrl);
    
        return  retrofit;
    }
    
    /**
     * 初始化 OkHttpClient
     *
     * @param connectTime
     * @param headerInterceptor
     * @return
     */
    private OkHttpClient initOkHttpClient(int connectTime, Interceptor headerInterceptor)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTime, TimeUnit.SECONDS);
        builder.addInterceptor(headerInterceptor);
        if (BuildConfig.DEBUG)
        {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }
        
        return builder.build();
    }
    
    /**
     * 初始化 Retrofit
     *
     * @param okHttpClient
     * @param baseUrl
     * @return
     */
    private Retrofit initRetrofit(OkHttpClient okHttpClient, String baseUrl)
    {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }
    
    /**
     * RxRetrofit处理
     *
     * @param observable
     * @param basePar
     */
    public void httpDeal(Observable observable, BaseApi basePar)
    {
        // 失败后的retry配置
        observable = observable.retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(), basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                // 异常处理
                .onErrorResumeNext(new ExceptionFunc())
                // 生命周期管理
                //.compose(appCompatActivity.get().bindToLifecycle())
                // Note:手动设置在activity onDestroy的时候取消订阅
                .compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))
                // 返回数据统一判断
                .map(new ResulteFunc())
                // http请求线程
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                // 回调线程
                .observeOn(AndroidSchedulers.mainThread());
        
         // observable回调链接式返回
        if (onNextSubListener != null && null != onNextSubListener.get()) {
            onNextSubListener.get().onNext(observable, basePar.getMethod());
        }
        
        // 数据String回调
        if (onNextListener != null && null != onNextListener.get())
        {
            ProgressSubscriber subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity);
            observable.subscribe(subscriber);
        }
    }
    
    /**
     * 日志打印拦截器
     *
     * @return
     */
    public HttpLoggingInterceptor getHttpLoggingInterceptor()
    {
        // 新建日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 日志显示级别
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        
        return loggingInterceptor;
    }
    
}
