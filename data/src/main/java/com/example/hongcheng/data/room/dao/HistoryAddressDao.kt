package com.example.hongcheng.data.room.dao

import android.arch.persistence.room.*
import com.example.hongcheng.data.room.entity.HistoryAddressEntity
import io.reactivex.Flowable

@Dao
interface HistoryAddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(data: List<HistoryAddressEntity>) : List<Long>

    @Query("UPDATE historyAddress SET isHome = :isHome where userNo = :userNo")
    fun updateHomeAddress(isHome: Boolean, userNo : String) : Int

    @Query("UPDATE historyAddress SET isCompany = :isCompany where userNo = :userNo")
    fun updateCompanyAddress(isCompany: Boolean, userNo : String) : Int

    @Query("SELECT * FROM historyAddress where userNo = :userNo")
    fun getAllInfoByUserNo(userNo : String): Flowable<List<HistoryAddressEntity>>
}