package com.ikoon.common.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.ikoon.common.app.AppBaseActivity;
import com.ikoon.common.R;
import com.ikoon.common.api.GlobalUrls;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.ikoon.common.contract.MvpContract;
import com.ikoon.common.model.MvpModel;
import com.ikoon.common.presenter.MvpPresenter;

/**
 * Created by MrKong on 2017/10/26.
 */

public class MvpActivity extends AppBaseActivity<MvpModel, MvpPresenter> implements MvpContract.View, View.OnClickListener
{
    
    private TextView tv_test1;
    private TextView tv_test2;
    private TextView tv_test3;
    
    @Override
    protected void initPresenter()
    {
        mPresenter.setVM(this, mModel);
    }
    
    @Override
    public void initData(Bundle bundle)
    {
        
    }
    
    @Override
    protected void initView()
    {
        tv_test1 = findViewById(R.id.tv_test1);
        tv_test2 = findViewById(R.id.tv_test2);
        tv_test3 = findViewById(R.id.tv_test3);
    }
    
    @Override
    protected void initEvent()
    {
        tv_test1.setOnClickListener(this);
        tv_test2.setOnClickListener(this);
        tv_test3.setOnClickListener(this);
    }
    
    @Override
    public void doBusiness(Context context)
    {
        
    }
    
    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_mvp;
    }
    
    
    @Override
    public void onClick(View v)
    {
        
        switch (v.getId())
        {
            case R.id.tv_test1:
                
                break;
            
            case R.id.tv_test2:             //get 请求
                mPresenter.getTest("ztctest01", "tyu567", MvpActivity.this);
//                toastUtils.showToast("tv_test2");
                break;
            
            case R.id.tv_test3:             //post 请求
                mPresenter.postTest(new HttpRequestEntityMessageInfos(1, 10), MvpActivity.this);
//                toastUtils.showToast("tv_test3");
    
                break;
            
        }
    }
    
    /**
     * 请求成功
     *
     * @param result
     * @param method
     */
    @Override
    public void httpSuccess(String result, String method)
    {
        switch (method)
        {
            case GlobalUrls.LOGIN_URL:
                tv_test2.setText(result);
                break;
            
            case GlobalUrls.GET_MESSAGE_INFO:
                tv_test3.setText(result);
                break;
        }
    }
    
    /**
     * 请求失败
     *
     * @param e
     * @param method
     */
    @Override
    public void httpFailed(ApiException e, String method)
    {
        switch (method)
        {
            case GlobalUrls.LOGIN_URL:
                tv_test2.setText(e.toString());
                break;
            
            case GlobalUrls.GET_MESSAGE_INFO:
                tv_test3.setText(e.toString());
                break;
        }
    }
    
    
}