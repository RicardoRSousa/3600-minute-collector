package com.ricardo.a3600collector.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ricardo.a3600collector.model.TimeData

/**
 * 3600-minute-collector
 * Created by Ricardo on 22/12/2018 14:28.
 */
@Database(entities = arrayOf(TimeData::class), version = 1)
abstract class TimeDatabase : RoomDatabase() {

    abstract fun weatherDataDao(): TimeDataDao

    companion object {
        private var INSTANCE: TimeDatabase? = null

        fun getInstance(context: Context): TimeDatabase? {
            if (INSTANCE == null) {
                synchronized(TimeDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TimeDatabase::class.java, "time.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}