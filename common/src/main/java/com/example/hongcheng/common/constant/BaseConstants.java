package com.example.hongcheng.common.constant;

import java.io.File;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseConstants {

    //debug开关
    public static final boolean DEBUG = true;

    //成功标志
    public static final int STATUS_SUCCESS = 0;

    //RecyclerView限制个数及开关
    public static final boolean IS_LIMIT = true;
    public static final int LIMIT_NUM = 30;

    //日志相关
    public static final String BASE_FILE_PATH = "LearnKotlin";
    public static final String LOG_PATH = BASE_FILE_PATH + File.separator + "log";
    public static final String LOG_FILE = BASE_FILE_PATH + ".log";
    public static final String CRASH_PATH = BASE_FILE_PATH + File.separator + "crash";
    public static final String APK_PATH = BASE_FILE_PATH + File.separator + "apk";

    //屏幕相关
    public static final String URI_PROVIDER = ".common.provider";

    //屏幕相关
    public static final String SCREEN_WIDTH = "SCREEN_WIDTH";
    public static final String SCREEN_HEIGHT = "SCREEN_HEIGHT";
}
