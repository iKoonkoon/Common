package com.ikoon.common.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;

import com.ikoon.common.R;
import com.ikoon.common.app.AppBaseActivity;

/**
 * @author MrKong
 * @date 2018/3/14
 */

public class TestActivity extends AppBaseActivity
{
    String TAG = "Test 1";
    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "执行onCreate");
    }
    
    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.e(TAG, "执行onRestart");
        
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.e(TAG, "执行onStart");
        
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.e(TAG, "执行onResume");
        
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.e(TAG, "执行onPause");
        
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.e(TAG, "执行onStop");
        
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.e(TAG, "执行onDestroy");
    }
    
    
    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }
    
    @Override
    protected void initPresenter()
    {
        
    }
    
    @Override
    public void initData(Bundle bundle)
    {
        
    }
    
    @Override
    protected void initView()
    {
        
    }
    
    @Override
    protected void initEvent()
    {
        
    }
    
    @Override
    public void doBusiness(Context context)
    {
    
        final Handler mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                Log.e(TAG, msg.what + "");
            }
        };
    
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Message message = new Message();
                message.obj = "测试数据";
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }).start();
    }
    
    /**
     * 实现Runnable接口
     * 重写run方法
     *
     * // 启动线程 new Thread(new TestRunnable()).start();
     *
     */
    public class TestRunnable implements Runnable {
        
        @Override
        public void run()
        {
            // 打印线程名称
            Log.e(TAG, Thread.currentThread().getName());
        }
    }
    
    /**
     * 继承Thread类
     * 重写run方法
     *
     * // 启动线程 定义线程名称   new TestThread("MrKong").start();
     *
     */
    public class TestThread extends Thread
    {
        public TestThread(String Str)
        {
            super(Str);
        }
    
        @Override
        public void run()
        {
            super.run();
            // 打印线程名称
            Log.e(TAG, Thread.currentThread().getName());
        }
    }
    
    /**
     * 通过Handler启动线程
     * // mHandler.post(mRunnable);
     *
     */
    private Handler mHandler  = new Handler();
    
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // 打印线程名称
            Log.e(TAG, Thread.currentThread().getName());
            // 自发消息自运行
            mHandler.postDelayed(mRunnable, 3000);
        }
    };
    
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG, "执行onSaveInstanceState");
        
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "执行onRestoreInstanceState");
    }
}
