package com.example.hongcheng.data.room.dao

import android.arch.persistence.room.*
import com.example.hongcheng.data.room.entity.CardInfoEntity
import io.reactivex.Flowable

@Dao
interface CardInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(cards: List<CardInfoEntity>) : List<Long>

    @Delete
    fun deleteByModel(cards: List<CardInfoEntity>) : Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateModel(cards: List<CardInfoEntity>) : Int

    @Query("SELECT * FROM cardInfo")
    fun getAllInfo(): Flowable<List<CardInfoEntity>>

    @Query("SELECT * FROM cardInfo where type = :type and cardType = :cardType")
    fun getAllInfoByCard(type : String, cardType : String): Flowable<List<CardInfoEntity>>
}