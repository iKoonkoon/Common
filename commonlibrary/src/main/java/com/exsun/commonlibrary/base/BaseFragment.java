package com.exsun.commonlibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exsun.commonlibrary.utils.other.TUtil;
import com.exsun.commonlibrary.utils.toast.ToastUtils;

/**
 *
 * @author MrKong
 * @date 2018/1/8
 */

public abstract class BaseFragment extends Fragment
{
    private static final String TAG = "BaseFragment";
    public static final String STATE_SAVE_IS_HIDDEN = "state_save_is_hidden";
   
    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;
    protected BaseActivity mActivity;
    public ToastUtils toastUtils;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden)
            {
                ft.hide(this);
            } else
            {
                ft.show(this);
            }
            ft.commit();
        }
        Log.d(TAG, "onCreate: ");
    }
    
    /**
     * 通过Class跳转界面
     * 不含Bundle
     *
     * @param cls
     */
    public void startActivity(Class<?> cls)
    {
        startActivity(cls, null);
    }
    
    /**
     * 通过Class跳转界面
     * 含有Bundle
     *
     * @param cls    跳转到的页面
     * @param bundle 包含有传递的参数
     */
    public void startActivity(Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(mActivity, intent, null);
    }
    
    /**
     * 通过Class跳转界面 有回调
     * 不含Bundle
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode)
    {
        startActivityForResult(cls, null, requestCode);
    }
    
    /**
     * 通过Class跳转界面 有回调
     * 含有Bundle
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode)
    {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (outState != null)
        {
            outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
        }
    }
    
}