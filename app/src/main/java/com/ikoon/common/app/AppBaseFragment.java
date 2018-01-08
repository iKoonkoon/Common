package com.ikoon.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exsun.commonlibrary.base.BaseActivity;
import com.exsun.commonlibrary.base.BaseFragment;
import com.exsun.commonlibrary.base.BaseModel;
import com.exsun.commonlibrary.base.BasePresenter;
import com.exsun.commonlibrary.base.BaseView;
import com.exsun.commonlibrary.utils.other.TUtil;
import com.exsun.commonlibrary.utils.toast.ToastUtils;

/**
 * Created by MrKong on 2018/1/8.
 */

public abstract class AppBaseFragment<M extends BaseModel, P extends BasePresenter> extends BaseFragment implements BaseView
{
    public M mModel;
    public P mPresenter;
    
    private static final String TAG = "BaseFragment";
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setRetainInstance(true);
        if (contentView == null)
        {
            contentView = inflater.inflate(getLayoutId(), null);
        }
        
        Log.d(TAG, "onCreateView: ");
        
        return contentView;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
        initView(savedInstanceState, contentView);
        Log.d(TAG, "onViewCreated: ");
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        toastUtils = new ToastUtils();
        doBusiness(mActivity);
        Log.d(TAG, "onActivityCreated: ");
    }
    
    @Override
    public void onDestroyView()
    {
        if (contentView != null)
        {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
    
    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化presenter层
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
    public abstract void initView(Bundle savedInstanceState, final View view);

    /**
     * 业务操作
     *
     * @param context
     */
    public abstract void doBusiness(Context context);
}
