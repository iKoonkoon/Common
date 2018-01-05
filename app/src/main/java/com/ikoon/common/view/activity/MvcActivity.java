package com.ikoon.common.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.google.gson.Gson;
import com.ikoon.common.R;
import com.ikoon.common.api.GlobalUrls;
import com.ikoon.common.api.TestAPI;
import com.ikoon.common.bean.BaseResultEntity;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.ikoon.common.bean.SubjectResulte;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

/**
 *
 * @author MrKong
 * @date 2017/9/13
 */

public class MvcActivity extends RxAppCompatActivity implements HttpOnNextListener
{
    
    private TextView tv_test;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private Button btn_request;
    private static final String TAG = "RxPermissionsSample";
    private TestAPI testAPI;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        btn_request = findViewById(R.id.btn_request);
        
        testAPI = new TestAPI(this, this);
        testAPI.getApi("ztctest01", "tyu567");

    }

    
    @Override
    public void onNext(String result, String method)
    {
        switch (method)
        {
            case GlobalUrls.LOGIN_URL:
                BaseResultEntity<ArrayList<SubjectResulte>> subjectResulte = new Gson().fromJson(result, BaseResultEntity.class);
                tv_test.setText("统一post返回：\n" + subjectResulte.getReturnValue().getToken_type() + "\naccess_token" + subjectResulte.getReturnValue().getAccess_token());
                
                GlobalUrls.TOKEN_CHANGE = GlobalUrls.BEARER + subjectResulte.getReturnValue().getAccess_token();
                testAPI.postApi(new HttpRequestEntityMessageInfos(1, 10));
                break;
            
            case GlobalUrls.GET_MESSAGE_INFO:
                
                break;
            
            default:
                break;
        }
    }
    
    @Override
    public void onError(ApiException e, String method)
    {
        tv_test.setText("失败：" + method + "\ncode=" + e.getCode() + "\nmsg:" + e.getDisplayMessage());
    }
}
