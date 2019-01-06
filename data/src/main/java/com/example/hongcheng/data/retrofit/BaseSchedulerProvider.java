package com.example.hongcheng.data.retrofit;

import android.support.annotation.NonNull;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;


public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();

    @NonNull
    <T> FlowableTransformer<T, T> applyDBSchedulers();
}
