package com.xp.dc.xpdc.application

import android.support.multidex.MultiDexApplication
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
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

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}