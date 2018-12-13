package com.saref.hrchecker.domain.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "members")
data class Member(
    @PrimaryKey
    val id: Int,
    val phone: String,
    val city: String,
    val company: String,
    val position: String,
    val addition: String,
    val registeredDate: Long,
    val agreedByManager: String,
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    val email: String,
    val eventId: Int,
    var presentStatus: Boolean = false
)
