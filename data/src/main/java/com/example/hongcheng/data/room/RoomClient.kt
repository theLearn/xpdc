package com.example.hongcheng.data.room

import com.example.hongcheng.data.retrofit.BaseFlowableSubscriber
import com.example.hongcheng.data.retrofit.ExceptionHandler
import com.example.hongcheng.data.retrofit.SchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import org.reactivestreams.Publisher

class RoomClient private constructor() {

    object SingleHolder {
        val INSTANCE : RoomClient = RoomClient()
    }

    companion object {
        fun getInstance() : RoomClient {
            return SingleHolder.INSTANCE
        }
    }

    fun <T> create (source : FlowableOnSubscribe<T>, subscriber : BaseFlowableSubscriber<T>) : Disposable {
        val o = Flowable.create(source, BackpressureStrategy.BUFFER)
                .compose(transformer())
                .compose(SchedulerProvider.getInstance().applyDBSchedulers())
        o.subscribe(subscriber)
        return subscriber
    }

    fun <T> map (followable : Flowable<T>, subscriber : BaseFlowableSubscriber<T>) : Disposable {
        val o = followable
                .compose(transformer())
                .compose(SchedulerProvider.getInstance().applyDBSchedulers())
        o.subscribe(subscriber)
        return subscriber
    }

    fun <T> transformer(): FlowableTransformer<T, T> {

        return object : FlowableTransformer<T, T> {
            override fun apply(upstream: Flowable<T>): Publisher<T> {
                return upstream.onErrorResumeNext(ResponseErrorFunc<T>())
            }
        }
    }

    class ResponseErrorFunc<T> : Function<Throwable, Publisher<out T>> {

        override fun apply(throwable: Throwable): Publisher<out T> {
            return Flowable.error(ExceptionHandler.handleException(throwable))
        }
    }
}