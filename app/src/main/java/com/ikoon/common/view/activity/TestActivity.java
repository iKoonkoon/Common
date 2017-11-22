package com.ikoon.common.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exsun.commonlibrary.frame.network.exception.ApiException;
import com.exsun.commonlibrary.frame.network.listener.HttpOnNextListener;
import com.google.gson.Gson;
import com.ikoon.common.R;
import com.ikoon.common.api.GlobalUrls;
import com.ikoon.common.api.TestAPI;
import com.ikoon.common.bean.BaseResultEntity;
import com.ikoon.common.bean.HttpRequestEntityMessageInfos;
import com.ikoon.common.bean.SubjectResulte;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by MrKong on 2017/9/13.
 */

public class TestActivity extends RxAppCompatActivity implements HttpOnNextListener
{
    
    private TextView tv_test;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private Button btn_request;
    private static final String TAG = "RxPermissionsSample";
    private RxPermissions rxPermissions;
    private TestAPI testAPI;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        btn_request = findViewById(R.id.btn_request);
         /*初始化数据*/

//        HttpManager httpManager = new HttpManager(this, this);
//
//        postEntity = new SubjectPostApi();
//        postEntity.setAll(true);
//
//        /*上传接口内部接口有token验证，所以需要换成自己的接口测试，检查file文件是否手机存在*/
//        uplaodApi = new UploadApi();
//        File file = new File("/storage/emulated/0/Download/11.jpg");
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file_name", file.getName(), new ProgressRequestBody
//                (requestBody, new UploadProgressListener() {
//                    @Override
//                    public void onProgress(final long currentBytesCount, final long totalBytesCount) {
//
//                        /*回到主线程中，可通过timer等延迟或者循环避免快速刷新数据*/
//                        Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
//
//                            @Override
//                            public void call(Long aLong) {
//                                tvMsg.setText("提示:上传中");
//                                progressBar.setMax((int) totalBytesCount);
//                                progressBar.setProgress((int) currentBytesCount);
//                            }
//                        });
//
//                    }
//                }));
//        uplaodApi.setPart(part);

//
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.show();
        
        testAPI = new TestAPI(this, this);
        testAPI.getApi("ztctest01", "tyu567");


//        testPermission();
    }
    
    private void testPermission()
    {
        
        rxPermissions = new RxPermissions(TestActivity.this);
        
        //请求单个权限
        
        btn_request.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Subscriber<Boolean>()
                        {
                            @Override
                            public void onCompleted()
                            {
                                
                            }
                            
                            @Override
                            public void onError(Throwable e)
                            {
                                
                            }
                            
                            @Override
                            public void onNext(Boolean granted)
                            {
                                if (granted)
                                {
                                    // All requested permissions are granted
                                    Log.i("permissions-me", Manifest.permission.READ_CALENDAR + "：" + "获取成功");
                                    
                                } else
                                {
                                    // At least one permission is denied
                                    Log.i("permissions-me", Manifest.permission.READ_CALENDAR + "：" + "获取失败");
                                    
                                }
                            }
                        });
            }
        });
        
    }
    
    /**
     * 点击按钮，将通讯录备份保存到外部存储器备。
     * <p>
     * 需要3个权限(都是危险权限):
     * 1. 读取通讯录权限;
     * 2. 读取外部存储器权限;
     * 3. 写入外部存储器权限.
     */
    public void click(View view)
    {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted)
        {
            doBackup();
            return;
        }
        
        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(TestActivity.this, new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSION_REQUEST_CODE
        );
    }
    
    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions)
    {
        for (String permission : permissions)
        {
            if (ContextCompat.checkSelfPermission(TestActivity.this, permission) != PackageManager.PERMISSION_GRANTED)
            {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * 第 4 步: 备份通讯录操作
     */
    private void doBackup()
    {
        // 本文主旨是讲解如果动态申请权限, 具体备份代码不再展示, 就假装备份一下
        Toast.makeText(TestActivity.this, "正在备份通讯录...", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
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
//                BaseApplication.mPref.put(Config.TOKEN, GlobalUrls.TOKEN_CHANGE);
                
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
