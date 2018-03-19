package com.ikoon.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.exsun.commonlibrary.base.BaseActivity;
import com.exsun.commonlibrary.base.BaseModel;
import com.exsun.commonlibrary.base.BasePresenter;
import com.exsun.commonlibrary.base.BaseView;
import com.exsun.commonlibrary.utils.dialog.TipLoadDialog;
import com.exsun.commonlibrary.utils.other.TUtil;
import com.exsun.commonlibrary.utils.view.progress.SVProgressHUD;

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
    public TipLoadDialog tipLoadDialog;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
     
        setContentView(getLayoutId());
        
        mModel = TUtil.getT(this, 0);
        mPresenter = TUtil.getT(this, 1);
    
        tipLoadDialog = new TipLoadDialog(this);
    
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
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();
    
    /**
     * 初始化presenter
     *
     */
    protected abstract void initPresenter();
    
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
     * 初始化listener
     *
     */
    protected abstract void initEvent();
    
    /**
     * 业务操作
     *
     * @param context
     */
    public abstract void doBusiness(Context context);
  
}