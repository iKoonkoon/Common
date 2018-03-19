package com.ikoon.common.view.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.google.gson.Gson;
import com.ikoon.common.R;
import com.ikoon.common.api.GlobalUrls;
import com.ikoon.common.api.TestAPI;
import com.ikoon.common.app.AppBaseActivity;
import com.ikoon.common.bean.BaseResultEntity;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.ikoon.common.bean.SubjectResulte;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

/**
 *
 * @author MrKong
 * @date 2017/9/13
 */

public class MvcActivity extends AppBaseActivity implements HttpOnNextListener
{
    
    private TestAPI testAPI;
    private TextView tv_test0;
    private TextView tv_test1;
    
    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_mvc;
    }
    
    @Override
    protected void initPresenter()
    {
        
    }
    
    @Override
    protected void initEvent()
    {
        
    }
    
    @Override
    public void initData(Bundle bundle)
    {
        
    }
    
    @Override
    protected void initView()
    {
        tv_test0 = (TextView) findViewById(R.id.tv_test0);
        tv_test1 = (TextView) findViewById(R.id.tv_test1);
    }
    
    @Override
    public void doBusiness(Context context)
    {
    
        testAPI = new TestAPI(this, this);
        testAPI.getApi("ztctest01", "tyu567");
    }
    
    
    @Override
    public void onNext(String result, String method)
    {
        switch (method)
        {
            case GlobalUrls.LOGIN_URL:
                BaseResultEntity<ArrayList<SubjectResulte>> subjectResulte = new Gson().fromJson(result, BaseResultEntity.class);
                tv_test0.setText("统一post返回：\n" + subjectResulte.getReturnValue().getToken_type() + "\naccess_token" + subjectResulte.getReturnValue().getAccess_token());
                
                GlobalUrls.TOKEN_CHANGE = GlobalUrls.BEARER + subjectResulte.getReturnValue().getAccess_token();
                testAPI.postApi(new HttpRequestEntityMessageInfos(1, 10));
                break;
            
            case GlobalUrls.GET_MESSAGE_INFO:
                
                break;
            
            default:
                break;
        }
    }
    
    @Override
    public void onError(ApiException e, String method)
    {
        tv_test0.setText("失败：" + method + "\ncode=" + e.getCode() + "\nmsg:" + e.getDisplayMessage());
    }
}
