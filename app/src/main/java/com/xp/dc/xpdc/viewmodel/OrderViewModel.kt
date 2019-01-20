package com.xp.dc.xpdc.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.example.hongcheng.common.base.BaseViewModel
import com.example.hongcheng.common.base.CommonUI
import com.xp.dc.xpdc.bean.CallCarInfo
import com.xp.dc.xpdc.bean.DriverInfo
import com.xp.dc.xpdc.bean.OrderInfo
import com.xp.dc.xpdc.bean.OrderState
import com.xp.dc.xpdc.location.XPLocation
import java.util.*


class OrderViewModel(private var ui: CommonUI?) : BaseViewModel(ui) {
    val order = MutableLiveData<OrderInfo>()

    companion object {
        public fun order() : OrderInfo{
            val startPosition = XPLocation()
            startPosition.name = "朗诗里程"
            startPosition.lat = 30.483613
            startPosition.lon = 114.522561
            val endPosition = XPLocation()
            endPosition.name = "武汉工程大学"
            endPosition.lat = 30.461216
            endPosition.lon = 114.428133
            val carInfo: MutableList<CallCarInfo> = arrayListOf()
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            return OrderInfo("123456789", OrderState.DEFAULT, Date().time, 5.0, 15, "20", startPosition, endPosition, carInfo, DriverInfo())
        }
    }



    public fun accept() {
        Handler().postDelayed({
            val startPosition = XPLocation()
            startPosition.name = "朗诗里程"
            val endPosition = XPLocation()
            endPosition.name = "武汉工程大学"
            val carInfo: MutableList<CallCarInfo> = arrayListOf()
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            val orderInfo = OrderInfo("123456789", OrderState.WAIT_CAR, Date().time, 5.0, 15, "20", startPosition, endPosition, carInfo, DriverInfo())
            order.postValue(orderInfo)
            start()
        }, 5000)
    }

    public fun start() {
        Handler().postDelayed({
            val startPosition = XPLocation()
            startPosition.name = "朗诗里程"
            val endPosition = XPLocation()
            endPosition.name = "武汉工程大学"
            val carInfo: MutableList<CallCarInfo> = arrayListOf()
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            val orderInfo = OrderInfo("123456789", OrderState.DRIVING, Date().time, 5.0, 15, "20", startPosition, endPosition, carInfo, DriverInfo())
            order.postValue(orderInfo)
            complete()
        }, 5000)
    }

    public fun complete() {
        Handler().postDelayed({
            val startPosition = XPLocation()
            startPosition.name = "朗诗里程"
            val endPosition = XPLocation()
            endPosition.name = "武汉工程大学"
            val carInfo: MutableList<CallCarInfo> = arrayListOf()
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            carInfo.add(CallCarInfo())
            val orderInfo = OrderInfo("123456789", OrderState.WAIT_PAY, Date().time, 5.0, 15, "20", startPosition, endPosition, carInfo, DriverInfo())
            order.postValue(orderInfo)
        }, 5000)
    }
}