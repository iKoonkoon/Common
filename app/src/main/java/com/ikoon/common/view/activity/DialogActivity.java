package com.ikoon.common.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exsun.commonlibrary.utils.dialog.TipLoadDialog;
import com.ikoon.common.R;
import com.ikoon.common.app.AppBaseActivity;

/**
 *
 * @author MrKong
 * @date 2018/2/6
 */

public class DialogActivity extends AppBaseActivity implements View.OnClickListener
{
    
    private Button success;
    private Button failed;
    private Button info;
    private Button loading;
    
    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_dialog;
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
        loading = (Button) findViewById(R.id.bt_loading);
        success = (Button) findViewById(R.id.bt_success);
        failed = (Button) findViewById(R.id.bt_failed);
        info = (Button) findViewById(R.id.bt_info);
    }
    
    @Override
    protected void initEvent()
    {
        loading.setOnClickListener(this);
        success.setOnClickListener(this);
        failed.setOnClickListener(this);
        info.setOnClickListener(this);
    }
    
    @Override
    public void doBusiness(Context context)
    {
        
    }
    
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
             
            case R.id.bt_loading:
                tipLoadDialog.setTextAndType(getString(R.string.loading), TipLoadDialog.ICON_TYPE_LOADING).show();
                break;
            
            case R.id.bt_success:
                tipLoadDialog.setTextAndType(getString(R.string.loading), TipLoadDialog.ICON_TYPE_SUCCESS).show();
                break;
            
            case R.id.bt_failed:
                tipLoadDialog.setTextAndType(getString(R.string.loading), TipLoadDialog.ICON_TYPE_FAIL).show();
                break;
            
            case R.id.bt_info:
                tipLoadDialog.setTextAndType(getString(R.string.loading), TipLoadDialog.ICON_TYPE_INFO).show();
                break;
            
            default:
                break;
        }
        
    }
}
