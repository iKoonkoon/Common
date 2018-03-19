package com.ikoon.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exsun.commonlibrary.base.BaseFragment;
import com.exsun.commonlibrary.base.BaseModel;
import com.exsun.commonlibrary.base.BasePresenter;
import com.exsun.commonlibrary.base.BaseView;
import com.exsun.commonlibrary.utils.other.TUtil;

/**
 *
 * @author MrKong
 * @date 2018/1/8
 */

public abstract class AppBaseFragment<M extends BaseModel, P extends BasePresenter> extends BaseFragment implements BaseView
{
    public M mModel;
    public P mPresenter;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null)
        {
            mPresenter.mContext = this.getActivity();
        }
        
        initPresenter();
        
        return mContentView;
    }
   
}
