package com.example.hongcheng.data.retrofit;

import com.example.hongcheng.common.util.LoggerUtils;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by hongcheng on 17/1/22.
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ActionException)
        {
            onError((ActionException)e);
        }
        else
        {
            LoggerUtils.error(BaseSubscriber.class.getName(), e.getMessage());
            onError(new ActionException(ExceptionHandler.ERROR.UNKNOWN, "unknow error"));
        }
    }

    @Override
    public void onNext(T t) {
        onBaseNext(t);
    }

    public abstract void onBaseNext(T t);
    public abstract void onError(ActionException e);
}
