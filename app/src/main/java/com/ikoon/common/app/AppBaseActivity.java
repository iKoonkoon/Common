package com.ikoon.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.exsun.commonlibrary.base.BaseActivity;
import com.exsun.commonlibrary.base.BaseModel;
import com.exsun.commonlibrary.base.BasePresenter;
import com.exsun.commonlibrary.base.BaseView;
import com.exsun.commonlibrary.utils.TUtil;

/**
 *
 * @author MrKong
 * @date 2017/10/26
 */

public abstract class AppBaseActivity<M extends BaseModel, P extends BasePresenter> extends BaseActivity implements BaseView
{
    public M mModel;
    public P mPresenter;
    public Context mContext;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        mModel = TUtil.getT(this, 0);
        mPresenter = TUtil.getT(this, 1);
    
        this.initPresenter();
        this.initData(getIntent().getExtras());
        this.initView();
        this.initEvent();
        this.doBusiness(this);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        if (mPresenter != null)
        {
            mPresenter.detachVM();
        }
    }
    
    /**
     * 初始化presenter
     *
     */
    protected abstract void initPresenter();
    
    /**
     * 初始化listener
     *
     */
    protected abstract void initEvent();
    
    /**
     * 初始化data
     *
     * @param bundle
     */
    public abstract void initData(Bundle bundle);
    
    /**
     * 初始化view
     *
     */
    protected abstract void initView();
    
    /**
     * 业务操作
     *
     * @param context
     */
    public abstract void doBusiness(Context context);
  
}