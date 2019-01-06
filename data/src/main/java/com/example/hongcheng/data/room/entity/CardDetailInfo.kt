package com.example.hongcheng.data.room.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class CardDetailInfo {
    @Embedded
    var cardEntity: CardEntity? = null

    @Relation(parentColumn = "type", entityColumn = "type")
    var cardInfoEntitys: List<CardInfoEntity> = arrayListOf()
}