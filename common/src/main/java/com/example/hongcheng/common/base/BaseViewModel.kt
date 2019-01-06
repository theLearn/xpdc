package com.example.hongcheng.common.base

import android.arch.lifecycle.ViewModel
import com.example.hongcheng.common.rx.RxUtils
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(private var ui: CommonUI?) : ViewModel() {

    protected var compositeDisposable: CompositeDisposable = RxUtils.getCompositeDisposable(this.javaClass)

    override fun onCleared() {
        super.onCleared()
        ui = null
        RxUtils.unsubscribe(compositeDisposable)
    }
}