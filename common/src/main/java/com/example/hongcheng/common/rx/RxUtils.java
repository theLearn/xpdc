package com.example.hongcheng.common.rx;

import io.reactivex.disposables.CompositeDisposable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by hongcheng on 16/3/30.
 */
public final class RxUtils {
    private final static ConcurrentMap<Class<?>, CompositeDisposable> OBSERVERS
            = new ConcurrentHashMap<>();

    private RxUtils() {

    }

    public static void unsubscribe(CompositeDisposable subscription) {
        if (subscription != null) {
            subscription.dispose();
        }
    }

    public static CompositeDisposable getCompositeDisposable(Class clazz) {
        CompositeDisposable subscription = null;
        if (OBSERVERS.containsKey(clazz)) {
            subscription = OBSERVERS.get(clazz);
        }

        if (subscription == null || subscription.isDisposed()) {
            subscription = new CompositeDisposable();
            OBSERVERS.putIfAbsent(clazz, subscription);
        }

        return subscription;
    }
}
