package com.example.hongcheng.data.retrofit;

/**
 * Created by hongcheng on 17/1/22.
 */
public abstract class RequestCallBack {
    public void onStart(){}
    public void onCompleted(){}
    public abstract void onSuccess();
    public abstract void onError(Exception e);
    public void progress(long currentSize){}
}
