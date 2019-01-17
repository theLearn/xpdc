package com.example.hongcheng.data.room.entity

import android.arch.persistence.room.Entity
import com.example.hongcheng.data.room.AppDatabase

@Entity(tableName = AppDatabase.HISTORY_ADDRESS_TABLE_NAME,
        primaryKeys = ["lat", "lon", "userNo", "type"])
data class HistoryAddressEntity(var lat: Double = 0.0,
                                var lon: Double = 0.0,
                                var userNo: String = "",
                                var type: Int = 0,
                                var name: String = "",
                                var address: String = "",
                                var province: String = "",
                                var city: String = "")