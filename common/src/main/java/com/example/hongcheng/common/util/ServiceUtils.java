package com.example.hongcheng.common.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about service
 * </pre>
 */
public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return all of the services are running.
     *
     * @return all of the services are running
     */
    public static Set getAllRunningServices(Context context) {
        ActivityManager am =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * Start the service.
     *
     * @param className The name of class.
     */
    public static void startService(Context context, final String className) {
        try {
            startService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the service.
     *
     * @param cls The service class.
     */
    public static void startService(Context context, final Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
    }

    /**
     * Stop the service.
     *
     * @param className The name of class.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(Context context, final String className) {
        try {
            return stopService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Stop the service.
     *
     * @param cls The name of class.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(Context context, final Class<?> cls) {
        Intent intent = new Intent(context, cls);
        return context.stopService(intent);
    }

    /**
     * Bind the service.
     *
     * @param className The name of class.
     * @param conn      The ServiceConnection object.
     * @param flags     Operation options for the binding.
     *                  <ul>
     *                  <li>0</li>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(Context context, final String className,
                                   final ServiceConnection conn,
                                   final int flags) {
        try {
            bindService(context, Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bind the service.
     *
     * @param cls   The service class.
     * @param conn  The ServiceConnection object.
     * @param flags Operation options for the binding.
     *              <ul>
     *              <li>0</li>
     *              <li>{@link Context#BIND_AUTO_CREATE}</li>
     *              <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *              <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *              <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *              <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *              <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *              </ul>
     */
    public static void bindService(Context context, final Class<?> cls,
                                   final ServiceConnection conn,
                                   final int flags) {
        Intent intent = new Intent(context, cls);
        context.bindService(intent, conn, flags);
    }

    /**
     * Unbind the service.
     *
     * @param conn The ServiceConnection object.
     */
    public static void unbindService(Context context, final ServiceConnection conn) {
        context.unbindService(conn);
    }

    /**
     * Return whether service is running.
     *
     * @param cls The service class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(Context context, final Class<?> cls) {
        return isServiceRunning(context, cls.getName());
    }

    /**
     * Return whether service is running.
     *
     * @param className The name of class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(Context context, final String className) {
        ActivityManager am =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }
}
