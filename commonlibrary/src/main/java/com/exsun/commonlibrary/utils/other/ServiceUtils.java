package com.exsun.commonlibrary.utils.other;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.exsun.commonlibrary.utils.app.AppUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * getAllRunningService: 获取所有运行的服务
 * startService        : 启动服务
 * stopService         : 停止服务
 * bindService         : 绑定服务
 * unbindService       : 解绑服务
 * isServiceRunning    : 判断服务是否运行
 *
 * @author MrKong
 * @date 2018/1/8
 */

public class ServiceUtils
{
    private ServiceUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 获取所有运行的服务
     *
     * @return 服务名集合
     */
    public static Set getAllRunningService()
    {
        ActivityManager am =
                (ActivityManager) AppUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null)
        {
            return Collections.emptySet();
        }
        List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0)
        {
            return null;
        }
        for (ActivityManager.RunningServiceInfo aInfo : info)
        {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }
    
    /**
     * 启动服务
     *
     * @param className 完整包名的服务类名
     */
    public static void startService(final String className)
    {
        try
        {
            startService(Class.forName(className));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 启动服务
     *
     * @param cls 服务类
     */
    public static void startService(final Class<?> cls)
    {
        Intent intent = new Intent(AppUtils.getApp(), cls);
        AppUtils.getApp().startService(intent);
    }
    
    /**
     * 停止服务
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(final String className)
    {
        try
        {
            return stopService(Class.forName(className));
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 停止服务
     *
     * @param cls 服务类
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(final Class<?> cls)
    {
        Intent intent = new Intent(AppUtils.getApp(), cls);
        return AppUtils.getApp().stopService(intent);
    }
    
    /**
     * 绑定服务
     *
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *                  <ul>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(final String className,
                                   final ServiceConnection conn,
                                   final int flags)
    {
        try
        {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 绑定服务
     *
     * @param cls   服务类
     * @param conn  服务连接对象
     * @param flags 绑定选项
     *              <ul>
     *              <li>{@link Context#BIND_AUTO_CREATE}</li>
     *              <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *              <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *              <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *              <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *              <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *              </ul>
     */
    public static void bindService(final Class<?> cls,
                                   final ServiceConnection conn,
                                   final int flags)
    {
        Intent intent = new Intent(AppUtils.getApp(), cls);
        AppUtils.getApp().bindService(intent, conn, flags);
    }
    
    /**
     * 解绑服务
     *
     * @param conn 服务连接对象
     */
    public static void unbindService(final ServiceConnection conn)
    {
        AppUtils.getApp().unbindService(conn);
    }
    
    /**
     * 判断服务是否运行
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isServiceRunning(final String className)
    {
        ActivityManager am =
                (ActivityManager) AppUtils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null)
        {
            return false;
        }
        List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0)
        {
            return false;
        }
        for (ActivityManager.RunningServiceInfo aInfo : info)
        {
            if (className.equals(aInfo.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }
}
