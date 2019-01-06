package com.example.hongcheng.data.retrofit;

import android.net.ParseException;

import com.example.hongcheng.common.util.LoggerUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by hongcheng on 17/1/22.
 */
public class ExceptionHandler {

    public static ActionException handleException(Throwable e) {
        LoggerUtils.error(ExceptionHandler.class.getName(), e.getMessage());
        ActionException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ActionException(httpException.code(), httpException.getMessage());
            return ex;
        } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
            ex = new ActionException(e, ERROR.TIMEOUT_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ActionException(e, ERROR.PARSE_ERROR, "解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ActionException(e, ERROR.NETWORK_ERROR, "连接失败");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ActionException(e, ERROR.SSL_ERROR, "证书验证失败");
            return ex;
        } else {
            ex = new ActionException(e, ERROR.UNKNOWN, "未知错误");
            return ex;
        }
    }


    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1003;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1004;
    }
}
