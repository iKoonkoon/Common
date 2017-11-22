package com.exsun.commonlibrary.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.Window;

import com.exsun.commonlibrary.utils.AppManager;
import com.exsun.commonlibrary.utils.StatusBarUtil;
import com.exsun.commonlibrary.utils.toast.ToastUtils;
import com.exsun.commonlibrary.utils.view.progress.SVProgressHUD;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by MrKong on 2017/9/11.
 */

public abstract class BaseActivity extends RxAppCompatActivity
{
    public ToastUtils toastUtils;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置布局之前配置
        doBeforeSetContentView();
        //设置布局
        setContentView(getLayoutId());
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        SVProgressHUD.mSVProgressHUD = null;
    }
    
    /**
     * setContentView之前配置
     *
     */
    public void doBeforeSetContentView()
    {
        // 设置昼夜主题
        setTheme();
        // 设置无标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置状态栏
        setStatusBar();
        // 把activity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 获取toast对象
        toastUtils = new ToastUtils();
    }
    
    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();
    
    /**
     * 设置状态栏
     *
     */
    public void setStatusBar()
    {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.ENVIOR_DEFAULT_STATUS_BAR_ALPHA);
    }
    
    /**
     * 设置主题
     *
     */
    public void setTheme()
    {
//        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme);
    }
    
    /**
     * 通过Class跳转界面
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
     * @param cls       跳转到的页面
     * @param bundle    包含有传递的参数
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
    
    /**
     * 展示弹框
     *
     * @param context
     * @param info
     */
    public void showDialog(Context context, String info)
    {
        SVProgressHUD.showWithStatus(context, info);
    }
    
    /**
     * 展示弹框
     *
     * @param context
     * @param info
     */
    public void showDialog(Context context, int info)
    {
        SVProgressHUD.showWithStatus(context, getString(info));
    }
    
    /**
     * 取消弹框
     *
     * @param context
     */
    public void dismissDialog(Context context)
    {
        if (SVProgressHUD.isShowing(this))
        {
            SVProgressHUD.dismiss(context);
        }
    }
    
    @Override
    public void onBackPressed()
    {
        if (SVProgressHUD.isShowing(this))
        {
            dismissDialog(this);
        } else
        {
            finish();
        }
    }
}
