package com.ricardo.a3600collector.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.ricardo.a3600collector.model.TimeData

/**
 * 3600-minute-collector
 * Created by Ricardo on 22/12/2018 14:26.
 */
@Dao
interface TimeDataDao {

    @Query("SELECT * from timeCombination")
    fun getAll(): List<TimeData>

    @Insert(onConflict = REPLACE)
    fun insert(timeCombination: TimeData)

    @Query("DELETE from timeCombination")
    fun deleteAll()
}