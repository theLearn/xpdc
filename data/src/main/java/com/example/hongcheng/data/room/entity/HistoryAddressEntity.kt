package com.example.hongcheng.data.room.entity

import android.arch.persistence.room.Entity
import com.example.hongcheng.data.room.AppDatabase

@Entity(tableName = AppDatabase.HISTORY_ADDRESS_TABLE_NAME,
        primaryKeys = ["lat", "lon", "userNo"])
data class HistoryAddressEntity(var lat: Double = 0.0,
                                var lon: Double = 0.0,
                                var userNo: String = "",
                                var isHome: Boolean = false,
                                var isCompany: Boolean = false,
                                var name: String = "",
                                var address: String = "",
                                var province: String = "",
                                var city: String = "")