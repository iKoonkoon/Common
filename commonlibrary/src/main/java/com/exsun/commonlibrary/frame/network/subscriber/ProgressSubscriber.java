package com.exsun.commonlibrary.frame.network.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.exsun.commonlibrary.base.BaseApi;
import com.exsun.commonlibrary.frame.network.cookie.CookieResult;
import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.exsun.commonlibrary.frame.network.exception.CodeException;
import com.exsun.commonlibrary.frame.network.exception.HttpTimeException;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.exsun.commonlibrary.frame.network.utils.AppUtil;
import com.exsun.commonlibrary.frame.network.utils.CookieDbUtil;


import java.lang.ref.SoftReference;

import rx.Observable;
import rx.Subscriber;

import static com.exsun.commonlibrary.base.BaseApplication.getApplication;


/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 *
 *
 * Created by MrKong on 2017/9/12.
 */

public class ProgressSubscriber<T> extends Subscriber<T>
{
    //  是否弹框
    private boolean showProgress = true;
    //  回调接口
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    //  软引用反正内存泄露
    private SoftReference<Context> mActivity;
    //  加载框可自己定义
    private ProgressDialog pd;
    //  请求数据
    private BaseApi api;
    
    
    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api, SoftReference<HttpOnNextListener> listenerSoftReference, SoftReference<Context> mActivity)
    {
        this.api = api;
        this.mSubscriberOnNextListener = listenerSoftReference;
        this.mActivity = mActivity;
        setShowProgress(api.isShowProgress());
        if (api.isShowProgress())
        {
            initProgressDialog(api.isCancel());
        }
    }
    
    
    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel)
    {
        Context context = mActivity.get();
        if (pd == null && context != null)
        {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel)
            {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialogInterface)
                    {
                        onCancelProgress();
                    }
                });
            }
        }
    }
    
    
    /**
     * 显示加载框
     */
    private void showProgressDialog()
    {
        if (!isShowProgress()) return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing())
        {
            pd.show();
        }
    }
    
    
    /**
     * 隐藏
     */
    private void dismissProgressDialog()
    {
        if (!isShowProgress()) return;
        if (pd != null && pd.isShowing())
        {
            pd.dismiss();
        }
    }
    
    
    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart()
    {
        showProgressDialog();
        //缓存并且有网
        if (api.isCache() && AppUtil.isNetworkAvailable(getApplication()))
        {
            //获取缓存数据
            CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            if (cookieResult != null)
            {
                long time = (System.currentTimeMillis() - cookieResult.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime())
                {
                    if (mSubscriberOnNextListener.get() != null)
                    {
                        mSubscriberOnNextListener.get().onNext(cookieResult.getResult(), api.getMethod());
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        }
    }
    
    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted()
    {
        dismissProgressDialog();
    }
    
    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e)
    {
        //需要緩存并且本地有缓存才返回
        if (api.isCache())
        {
            getCache();
        } else
        {
            errorDo(e);
        }
        dismissProgressDialog();
    }
    
    /**
     * 获取cache数据
     */
    private void getCache()
    {
        Observable.just(api.getUrl()).subscribe(new Subscriber<String>()
        {
            @Override
            public void onCompleted()
            {
                
            }
            
            @Override
            public void onError(Throwable e)
            {
                errorDo(e);
            }
            
            @Override
            public void onNext(String s)
            {
                //获取缓存数据
                CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieBy(s);
                if (cookieResult == null)
                {
                    throw new HttpTimeException(HttpTimeException.NO_CACHE_ERROR);
                }
                long time = (System.currentTimeMillis() - cookieResult.getTime()) / 1000;
                if (time < api.getCookieNoNetWorkTime())
                {
                    if (mSubscriberOnNextListener.get() != null)
                    {
                        mSubscriberOnNextListener.get().onNext(cookieResult.getResult(), api.getMethod());
                    }
                } else
                {
                    CookieDbUtil.getInstance().deleteCookie(cookieResult);
                    throw new HttpTimeException(HttpTimeException.CACHE_TIMEOUT_ERROR);
                }
            }
        });
    }
    
    
    /**
     * 错误统一处理
     *
     * @param e
     */
    private void errorDo(Throwable e)
    {
        Context context = mActivity.get();
        if (context == null) return;
        HttpOnNextListener httpOnNextListener = mSubscriberOnNextListener.get();
        if (httpOnNextListener == null) return;
        if (e instanceof ApiException)
        {
            httpOnNextListener.onError((ApiException) e, api.getMethod());
        } else if (e instanceof HttpTimeException)
        {
            HttpTimeException exception = (HttpTimeException) e;
            httpOnNextListener.onError(new ApiException(exception, CodeException.RUNTIME_ERROR, exception.getMessage()), api.getMethod());
        } else
        {
            httpOnNextListener.onError(new ApiException(e, CodeException.UNKNOWN_ERROR, e.getMessage()), api.getMethod());
        }
    }
    
    
    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t)
    {
        //缓存处理
        if (api.isCache())
        {
            CookieResult result = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            long time = System.currentTimeMillis();
            /*保存和更新本地数据*/
            if (result == null)
            {
                result = new CookieResult(api.getUrl(), t.toString(), time);
                CookieDbUtil.getInstance().saveCookie(result);
            } else
            {
                result.setResult(t.toString());
                result.setTime(time);
                CookieDbUtil.getInstance().updateCookie(result);
            }
        }
        if (mSubscriberOnNextListener.get() != null)
        {
            mSubscriberOnNextListener.get().onNext((String) t, api.getMethod());
        }
    }
    
    
    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress()
    {
        if (!this.isUnsubscribed())
        {
            this.unsubscribe();
        }
    }
    
    
    public boolean isShowProgress()
    {
        return showProgress;
    }
    
    /**
     * 是否需要弹框设置
     *
     * @param showProgress
     */
    public void setShowProgress(boolean showProgress)
    {
        this.showProgress = showProgress;
    }
    
}
