package com.example.hongcheng.common.base

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Created by hongcheng on 17/5/29.
 */
class BaseHandler<T : BaseHandler.BaseHandlerCallBack>(t : T) : Handler() {

    var wr : WeakReference<T> ? = null

    init {
        wr = WeakReference<T>(t)
    }

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)

        val t : T ?  = wr?.get()
        if (msg != null) {
            t?.callBack(msg)
        }
    }

    interface BaseHandlerCallBack {
        fun callBack(msg: Message)
    }
}