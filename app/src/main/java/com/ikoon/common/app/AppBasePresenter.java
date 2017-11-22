package com.ikoon.common.app;


import com.exsun.commonlibrary.base.BasePresenter;

/**
 * Created by MrKong on 2017/11/22.
 */

public abstract class AppBasePresenter<M, V> extends BasePresenter
{
    public M mModel;
    public V mView;
    
    @Override
    public void attachVM(Object o, Object o2)
    {
        super.attachVM(o, o2);
    }
    
    @Override
    public void setVM(Object o, Object o2)
    {
        super.setVM(o, o2);
    }
    
    @Override
    public void detachVM()
    {
        super.detachVM();
        mRxManager.clear();
    
    }
}
