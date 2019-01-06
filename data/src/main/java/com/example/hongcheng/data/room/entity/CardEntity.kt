package com.example.hongcheng.data.room.entity

import android.arch.persistence.room.Entity
import com.example.hongcheng.data.room.AppDatabase

@Entity(tableName = AppDatabase.CARD_TABLE_NAME,
        primaryKeys = ["type", "cardType"])
//        indices = [Index(value = ["type", "cardType"], unique = true)])
data class CardEntity(var type: String,
                      var cardType: String,
                      var name: String = "",
                      var description: String = "",
                      var imageUrl: String = "")