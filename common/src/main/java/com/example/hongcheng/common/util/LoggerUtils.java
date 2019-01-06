package com.example.hongcheng.common.util;


import android.support.annotation.Nullable;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by hongcheng on 16/4/2.
 */
public final class LoggerUtils {

    private LoggerUtils() {

    }

    public static void init(final boolean debug) {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return debug;
            }
        });
    }

    public static void debug(String tag, String message) {
        Logger.t(tag).d(message + "");
    }

    public static void debug(String tag, String message, Throwable t) {
        Logger.t(tag).d(message + "    Throwable :  " + t.getMessage());
    }

    public static void info(String tag, String message) {
        Logger.t(tag).i(message + "");
    }

    public static void info(String tag, String message, Throwable t) {
        Logger.t(tag).d(message + "    Throwable :  " + t.getMessage());
    }

    public static void warn(String tag, String message) {
        Logger.t(tag).w(message + "");
    }

    public static void warn(String tag, String message, Throwable t) {
        Logger.t(tag).w(message + "    Throwable :  " + t.getMessage());
    }

    public static void error(String tag, String message) {
        Logger.t(tag).e(message + "");
    }

    public static void error(String tag, String message, Throwable t) {
        Logger.e(t, message + "");
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void wtf(String wtf) {
        Logger.wtf(wtf);
    }
}
