package com.ikoon.common.app;

import com.exsun.commonlibrary.base.BaseApi;
import com.exsun.commonlibrary.frame.network.http.HttpManager;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextSubListener;
import com.ikoon.common.api.GlobalUrls;
import com.ikoon.common.api.HttpApiService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by MrKong on 2017/9/13.
 */

public class AppBaseApi extends BaseApi
{
    
    private final HttpManager httpManager;
    private HttpApiService httpService;
    
    @Override
    public Observable getObservable(Retrofit retrofit)
    {
        return null;
    }
    
    public AppBaseApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity)
    {
        httpManager = new HttpManager(onNextListener, appCompatActivity);
        setBaseToken(GlobalUrls.TOKEN_CHANGE);
        setBaseUrl(GlobalUrls.REALEASE_URL);
    }
    
    public AppBaseApi(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        httpManager = new HttpManager(onNextSubListener, appCompatActivity);
        setBaseToken(GlobalUrls.TOKEN_CHANGE);
        setBaseUrl(GlobalUrls.REALEASE_URL);
    }
    
    protected Retrofit getRetrofit()
    {
        return httpManager.getRetrofit(getBaseUrl(), getConnectionTime(), addHeader());
    }
    
    protected void doHttpDeal(Observable observable)
    {
        httpManager.httpDeal(observable, this);
    }
    
    /**
     * 添加头部信息
     *
     * @return
     */
    protected Interceptor addHeader()
    {
        Interceptor headerInterceptor = new Interceptor()
        {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", GlobalUrls.TOKEN_CHANGE)
                        .addHeader("AppType", "Android")
                        .build();
                return chain.proceed(build);
            }
        };
        return headerInterceptor;
    }
    
    /**
     *获取HttpApi
     *
     * @return
     */
    protected HttpApiService getHttpApi()
    {
        if (httpService != null)
        {
            return httpService;
        }
        
        httpService = getRetrofit().create(HttpApiService.class);
        
        return httpService;
    }
    
    
    
    
    
    
    
    
    
    /**
     * MVC 模式
     *
     */
//    private HttpManager manager;
//
//    @Override
//    public Observable getObservable(Retrofit retrofit)
//    {
//        return null;
//    }
//
//    public AppBaseApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
//        manager = new HttpManager(onNextListener, appCompatActivity);
//        setBaseToken(GlobalUrls.TOKEN_CHANGE);
//        setBaseUrl(GlobalUrls.REALEASE_URL);
//    }
//
//    protected HttpApiService getRetrofit() {
//        return  manager.getRetrofit(getConnectionTime(), getBaseUrl(), getBaseToken());
//    }
//
//    protected void doHttpDeal(Observable observable) {
//        manager.httpDeal(observable, this);
//    }
    
    
}