package com.xp.dc.xpdc.bean

import com.xp.dc.xpdc.location.XPLocation

data class DriverInfo(
    var name: String = "黄师傅",
    var imgUrl: String = "",
    var carNo: String = "粤B1U2A3",
    var carType: String = "滴滴快车-经济型",
    var carName: String = "奔驰"
)
data class CallCarInfo(
    var carType: String = "滴滴快车-经济型",
    var icon: Int = 0,
    var waitTip: String = ""
)

object OrderState{
    public const val DEFAULT: Int = 0
    public const val WAIT_CAR: Int = 1
    public const val DRIVING: Int = 2
    public const val WAIT_PAY: Int = 3
    public const val FINISH: Int = 4
    public const val FAIL: Int = 5
}

data class OrderInfo(
    var orderNo: String,
    var state: Int,
    var time: Long,
    var mileage: Double = 0.0,
    var driveTime: Int = 0,
    var price: String,
    var startPosition: XPLocation,
    var endPosition: XPLocation,
    var carInfo: MutableList<CallCarInfo> = arrayListOf(),
    var driverInfo : DriverInfo? = null
)