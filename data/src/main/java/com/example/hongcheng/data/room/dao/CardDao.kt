package com.example.hongcheng.data.room.dao

import android.arch.persistence.room.*
import com.example.hongcheng.data.room.entity.CardDetailInfo
import com.example.hongcheng.data.room.entity.CardEntity
import io.reactivex.Flowable


@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(cards: List<CardEntity>) : List<Long>

    @Delete
    fun deleteByModel(cards: List<CardEntity>) : Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateModel(cards: List<CardEntity>) : Int

    @Query("SELECT * FROM cards")
    fun getAllCards(): Flowable<List<CardEntity>>

    @Query("SELECT * FROM cards where cardType = :cardType")
    fun getCardsByType(cardType : String): Flowable<List<CardEntity>>

    @Transaction
    @Query("SELECT * FROM cards")
    fun getAllCardAndInfo(): Flowable<List<CardDetailInfo>>
}