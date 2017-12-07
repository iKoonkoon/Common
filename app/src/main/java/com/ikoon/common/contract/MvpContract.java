package com.ikoon.common.contract;

import com.exsun.commonlibrary.base.BaseModel;
import com.exsun.commonlibrary.base.BasePresenter;
import com.exsun.commonlibrary.base.BaseView;
import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.ikoon.common.api.MvpTestApi;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 *
 * @author MrKong
 * @date 2017/10/27
 */

public interface MvpContract
{
    interface Model extends BaseModel
    {
    
        /**
         * model层 响应 presenter层操作
         * get请求测试业务操作
         *
         * @param username
         * @param password
         * @param mvpTestApi
         */
        void startGet(String username, String password, MvpTestApi mvpTestApi);
        
        /**
         * model层 响应 presenter层操作
         * post请求测试业务操作
         *
         * @param httpRequestEntityMessageInfos
         * @param mvpTestApi
         */
        void startPost(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos, MvpTestApi mvpTestApi);
    
        /**
         * model层 响应 presenter层操作
         *
         * @param message
         */
        void testDo(String message);
    }
    
    interface View extends BaseView
    {
    
        /**
         * view页面 请求成功业务操作
         *
         * @param result
         * @param method
         */
        void httpSuccess(String result, String method);
    
        /**
         * view页面 请求失败业务操作
         *
         * @param e
         * @param method
         */
        void httpFailed(ApiException e, String method);
    }
    
    abstract class Presenter extends BasePresenter<Model, View>
    {
    
        /**
         * presenter层 响应view层操作
         * get请求测试
         *
         * @param username
         * @param password
         * @param activity
         */
        public abstract void httpGetTest(String username, String password, RxAppCompatActivity activity);
    
        /**
         * presenter层 响应view层操作
         * post请求测试
         *
         * @param httpRequestEntityMessageInfos
         * @param activity
         */
        public abstract void httpPostTest(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos, RxAppCompatActivity activity);
    }
}
