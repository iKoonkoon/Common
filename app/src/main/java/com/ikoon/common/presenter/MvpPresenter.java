package com.ikoon.common.presenter;

import android.util.Log;

import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.google.gson.Gson;
import com.ikoon.common.api.GlobalUrls;
import com.ikoon.common.api.MvpTestApi;
import com.ikoon.common.bean.BaseResultEntity;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.ikoon.common.bean.SubjectResulte;
import com.ikoon.common.contract.MvpContract;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import static com.ikoon.common.config.Constant.MVP_PRESENTER_TAG;

/**
 * @author MrKong
 * @date 2017/10/27
 */

public class MvpPresenter extends MvpContract.Presenter implements HttpOnNextListener
{
    
    @Override
    public void httpGetTest(String username, String password, RxAppCompatActivity activity)
    {
        MvpTestApi mvpTestApi = new MvpTestApi(this, activity);
        mModel.startGet(username, password, mvpTestApi);
    }
    
    @Override
    public void httpPostTest(HttpRequestEntityMessageInfos httpRequestEntityMessageInfos, RxAppCompatActivity activity)
    {
        MvpTestApi mvpTestApi = new MvpTestApi(this, activity);
        mModel.startPost(httpRequestEntityMessageInfos, mvpTestApi);
    }
    
    @Override
    public void onNext(String result, String method)
    {
        Log.d(MVP_PRESENTER_TAG, method);
        if (method == GlobalUrls.LOGIN_URL)
        {
            BaseResultEntity<ArrayList<SubjectResulte>> subjectResult = new Gson().fromJson(result, BaseResultEntity.class);
            GlobalUrls.TOKEN_CHANGE = GlobalUrls.BEARER + subjectResult.getReturnValue().getAccess_token();
            mView.httpSuccess(result, method);
        }
    }
    
    @Override
    public void onError(ApiException e, String method)
    {
        Log.d(MVP_PRESENTER_TAG, method);
        if (method == GlobalUrls.LOGIN_URL)
        {
            mView.httpFailed(e, method);
        }
    }
}
