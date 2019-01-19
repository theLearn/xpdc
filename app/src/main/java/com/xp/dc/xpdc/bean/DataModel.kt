package com.xp.dc.xpdc.bean

import com.xp.dc.xpdc.location.XPLocation


data class WaitCallCarViewModel(
    var orderNo: String = "123456789",
    var userImgUrl: String = "",
    var carTypeNum: String = "1",
    var carType: String = "滴滴快车-经济型",
    var carImgUrl: String = "",
    var limitTime: Long = 10000
)

data class OrderInfo(
    var orderNo: String,
    var state: Int,
    var time: Long,
    var startPosition: XPLocation,
    var endPosition: XPLocation,
    var carInfo: CarInfo?
)