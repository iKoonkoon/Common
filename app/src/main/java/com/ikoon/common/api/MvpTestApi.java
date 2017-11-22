package com.ikoon.common.api;

import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextSubListener;
import com.ikoon.common.app.AppBaseApi;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MrKong on 2017/10/27.
 */

public class MvpTestApi extends AppBaseApi
{
    public MvpTestApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity)
    {
        super(onNextListener, appCompatActivity);
    }
    
    public MvpTestApi(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity)
    {
        super(onNextSubListener, appCompatActivity);
    }
    
    /**
     * get请求演示
     *
     */
    public void getApi(final String username, final String password) {
        setCache(false);
        setMethod(GlobalUrls.LOGIN_URL);
        doHttpDeal(getHttpApi().loginService(username, password));
    }
    
    /**
     * post请求演示
     *
     */
    public void postApi(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos) {
        setCache(false);
        setMethod(GlobalUrls.GET_MESSAGE_INFO);
        doHttpDeal(getHttpApi().messageInfos(httpRequestEntityMessageInfos));
    }
    
}
