package com.ikoon.common.view.activity;

import android.content.Context;
import android.nfc.Tag;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.exsun.commonlibrary.utils.log.LogUtils;
import com.ikoon.common.R;
import com.ikoon.common.app.AppBaseActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author MrKong
 */
public class MainActivity extends AppBaseActivity
{
    String TAG = "Test 0";
    private TextView tv_test;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Log.e(TAG, "执行onCreate");
    
//        FormatStrategy formatStrategy  = PrettyFormatStrategy.newBuilder().tag("Test").build();
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
//        Logger.d("执行onCreate");
    }
    
    @Override
    protected void onRestart()
    {
        super.onRestart();
//        Logger.d("执行onRestart");
        Log.e(TAG, "执行onRestart");
    
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
//        Logger.d("执行onStart");
        Log.e(TAG, "执行onStart");
    
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
//        Logger.d("执行onResume");
        Log.e(TAG, "执行onResume");
    
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
//        Logger.d("执行onPause");
        Log.e(TAG, "执行onPause");
    
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
//        Logger.d("执行onStop");
        Log.e(TAG, "执行onStop");
    
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
//        Logger.d("执行onDestroy");
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
        tv_test = (TextView) findViewById(R.id.tv_test);
    }
    
    @Override
    protected void initEvent()
    {
        
        tv_test.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(TestActivity.class);
            }
        });
    }
    
    @Override
    public void doBusiness(Context context)
    {
        
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
//        Logger.d("执行onSaveInstanceState");
        Log.e(TAG, "执行onSaveInstanceState");
    
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
//        Logger.d("执行onRestoreInstanceState");
        Log.e(TAG, "执行onRestoreInstanceState");
    }
    
}
