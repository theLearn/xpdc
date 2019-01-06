package com.example.hongcheng.data.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.example.hongcheng.data.room.AppDatabase

@Entity(tableName = AppDatabase.CARD_INFO_TABLE_NAME,
        foreignKeys = [(ForeignKey(entity = CardEntity::class, parentColumns = ["type", "cardType"], childColumns = ["type", "cardType"], onDelete = ForeignKey.CASCADE))])
data class CardInfoEntity (var type: String,
                           var cardType: String,
                           var name: String = "",
                           var content: String = "",
                           var date: String = "",
                           var description: String = "",
                           var imageUrl: String = "")
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var infoId: Long = 0
}