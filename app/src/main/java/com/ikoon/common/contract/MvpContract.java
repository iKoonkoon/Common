package com.ikoon.common.contract;

import com.exsun.commonlibrary.base.BaseModel;
import com.exsun.commonlibrary.base.BasePresenter;
import com.exsun.commonlibrary.base.BaseView;
import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.ikoon.common.api.MvpTestApi;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MrKong on 2017/10/27.
 */

public interface MvpContract
{
    interface Model extends BaseModel
    {

        void startPost(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos, MvpTestApi mvpTestApi);
        
        void startGet(String username, String password, MvpTestApi mvpTestApi);

        void testDo(String message);
    }
    
    interface View extends BaseView
    {
    
        void httpSuccess(String result, String method);
    
        void httpFailed(ApiException e, String method);
    }
    
    abstract class Presenter extends BasePresenter<Model, View>
    {
        
        public abstract void postTest(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos, RxAppCompatActivity activity);
        
        public abstract void getTest(String username, String password, RxAppCompatActivity activity);
    }
}
