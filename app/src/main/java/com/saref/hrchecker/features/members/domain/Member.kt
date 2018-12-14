package com.saref.hrchecker.features.members.domain

import android.arch.persistence.room.Entity
import java.io.Serializable

@Entity(tableName = "members", primaryKeys = ["id","eventId"])
data class Member(
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
    var presentStatus: Boolean = false,
    var visitedDate: String = ""
): Serializable
