package com.example.hongcheng.data.room.dao

import android.arch.persistence.room.*
import com.example.hongcheng.data.room.entity.HistoryAddressEntity
import io.reactivex.Flowable

@Dao
interface HistoryAddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(data: List<HistoryAddressEntity>) : List<Long>

    @Delete
    fun deleteByModel(data: List<HistoryAddressEntity>) : Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateModel(data: List<HistoryAddressEntity>) : Int

    @Query("SELECT * FROM historyAddress where userNo = :userNo")
    fun getAllInfoByUserNo(userNo : String): Flowable<List<HistoryAddressEntity>>

    @Query("SELECT * FROM historyAddress where type = :type and userNo = :userNo")
    fun getAllInfoByType(userNo : String, type : Int): Flowable<List<HistoryAddressEntity>>
}