package com.ikoon.common.app;

import com.exsun.commonlibrary.base.BaseApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 *
 * @author MrKong
 * @date 2017/11/22
 */

public class AppBaseApplication extends BaseApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
   
//        Logger.addLogAdapter(new AndroidLogAdapter());
        
    }
}
