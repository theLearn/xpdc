package com.xp.dc.xpdc.application

import android.support.multidex.MultiDexApplication
import com.example.hongcheng.common.lifecycle.ActivityLifecycleImpl
import com.example.hongcheng.common.util.ResUtil
import com.example.hongcheng.data.room.DBInit

class BaseApplication : MultiDexApplication(){

    private object  BaseApplicationHolder {
        var INSTANCE : BaseApplication? = null
    }
    companion object {
        @JvmStatic
        fun getInstance() : BaseApplication? = BaseApplicationHolder.INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        BaseApplicationHolder.INSTANCE = this
        ResUtil.init(this)
        registerActivityLifecycleCallbacks(ActivityLifecycleImpl.getInstance())
        DBInit.getInstance().init(this)
    }
}