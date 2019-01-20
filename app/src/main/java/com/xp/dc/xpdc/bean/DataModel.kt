package com.xp.dc.xpdc.bean

import android.os.Parcel
import android.os.Parcelable
import com.xp.dc.xpdc.location.XPLocation

data class DriverInfo(
    var name: String = "黄师傅",
    var imgUrl: String = "",
    var carNo: String = "粤B1U2A3",
    var carType: String = "滴滴快车-经济型",
    var carName: String = "奔驰"
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(imgUrl)
        writeString(carNo)
        writeString(carType)
        writeString(carName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DriverInfo> = object : Parcelable.Creator<DriverInfo> {
            override fun createFromParcel(source: Parcel): DriverInfo = DriverInfo(source)
            override fun newArray(size: Int): Array<DriverInfo?> = arrayOfNulls(size)
        }
    }
}

data class CallCarInfo(
    var carType: String = "滴滴快车-经济型",
    var icon: Int = 0,
    var waitTip: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(carType)
        writeInt(icon)
        writeString(waitTip)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CallCarInfo> = object : Parcelable.Creator<CallCarInfo> {
            override fun createFromParcel(source: Parcel): CallCarInfo = CallCarInfo(source)
            override fun newArray(size: Int): Array<CallCarInfo?> = arrayOfNulls(size)
        }
    }
}

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
    var driverInfo: DriverInfo? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        source.readLong(),
        source.readDouble(),
        source.readInt(),
        source.readString(),
        source.readParcelable<XPLocation>(XPLocation::class.java.classLoader),
        source.readParcelable<XPLocation>(XPLocation::class.java.classLoader),
        source.createTypedArrayList(CallCarInfo.CREATOR),
        source.readParcelable<DriverInfo>(DriverInfo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(orderNo)
        writeInt(state)
        writeLong(time)
        writeDouble(mileage)
        writeInt(driveTime)
        writeString(price)
        writeParcelable(startPosition, 0)
        writeParcelable(endPosition, 0)
        writeTypedList(carInfo)
        writeParcelable(driverInfo, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OrderInfo> = object : Parcelable.Creator<OrderInfo> {
            override fun createFromParcel(source: Parcel): OrderInfo = OrderInfo(source)
            override fun newArray(size: Int): Array<OrderInfo?> = arrayOfNulls(size)
        }
    }
}