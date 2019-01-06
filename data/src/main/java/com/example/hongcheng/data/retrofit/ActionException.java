package com.example.hongcheng.data.retrofit;

/**
 * Created by hongcheng on 17/1/22.
 */
public class ActionException extends Exception
{
    // 默认序列号
    private static final long serialVersionUID = 1L;

    // 错误码
    private int errorCode;

    /**
     * @param errorCode 错误码
     * @param errorMessage 错误信息
     */
    public ActionException(int errorCode, String errorMessage)
    {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public ActionException(Throwable throwable, int errorCode, String errorMessage) {
        super(errorMessage, throwable);
        this.errorCode = errorCode;
    }

    /**
     * 获取错误码
     *
     * @return
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * 获取错误描述信息
     *
     * @return
     */
    public String getErrorMessage()
    {
        return this.getMessage();
    }

    @Override
    public String toString()
    {
        String name = getClass().getName();
        return name + ":: [errorCode=" + errorCode + "; errorMessage=" + getErrorMessage() + "].";
    }
}
