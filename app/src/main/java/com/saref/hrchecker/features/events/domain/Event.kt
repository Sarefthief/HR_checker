package com.saref.hrchecker.features.events.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val cardImage: String
)
