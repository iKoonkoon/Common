package com.exsun.commonlibrary.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;

import com.exsun.commonlibrary.utils.app.AppManager;
import com.exsun.commonlibrary.utils.other.StatusBarUtil;
import com.exsun.commonlibrary.utils.toast.ToastUtils;
import com.exsun.commonlibrary.utils.view.progress.SVProgressHUD;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * @author MrKong
 * @date 2017/9/11
 */

public abstract class BaseActivity extends RxAppCompatActivity
{
    public ToastUtils toastUtils;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        doBeforeSetContentView();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
    
    /**
     * setContentView之前配置
     */
    public void doBeforeSetContentView()
    {
        // 设置无标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置状态栏
        setStatusBar();
        // 栈中管理Activity
        AppManager.getAppManager().addActivity(this);
        // 获取Toast对象
        toastUtils = new ToastUtils();
    }
    
    /**
     * 设置状态栏
     */
    public void setStatusBar()
    {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.ENVIOR_DEFAULT_STATUS_BAR_ALPHA);
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
        intent.setClass(this, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(this, intent, null);
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
        intent.setClass(this, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    
}
