package com.ricardo.a3600collector.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * 3600-minute-collector
 * Created by Ricardo on 22/12/2018 14:23.
 */
@Entity(tableName = "timeCombination")
data class TimeData(@PrimaryKey(autoGenerate = true) var id: Long?,
                    @ColumnInfo(name = "time") var time: String) {

    constructor() : this(null, "")
}