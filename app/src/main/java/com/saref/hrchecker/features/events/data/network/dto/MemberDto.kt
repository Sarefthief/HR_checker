package com.saref.hrchecker.features.events.data.network.dto

data class MemberDto(
    val id: Int,
    val phone: String,
    val city: String,
    val company: String,
    val position: String,
    val addition: String,
    val registeredDate: String,
    val agreedByManager: String,
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    val email: String
)