package com.ikoon.common.api;

import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextSubListener;
import com.ikoon.common.app.AppBaseApi;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MrKong on 2017/9/13.
 */

public class TestAPI extends AppBaseApi
{
    public TestAPI(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity)
    {
        super(onNextListener, appCompatActivity);
    }
    
    public TestAPI(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity)
    {
        super(onNextSubListener, appCompatActivity);
    }

        
    /**
     * post请求演示
     *
     */
    public void postApi(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos) {
        setCache(false);
        setMethod(GlobalUrls.GET_MESSAGE_INFO);
//        doHttpDeal(getRetrofit().messageInfos(httpRequestEntityMessageInfos));
    }
    
    /**
     * get请求演示
     *
     */
    public void getApi(final String username, final String password) {
        setCache(false);
        setMethod(GlobalUrls.LOGIN_URL);
//        doHttpDeal(getRetrofit().loginService(username, password));
    }
}
