package com.saref.hrchecker.utils

import com.saref.hrchecker.data.network.dto.EventDto
import com.saref.hrchecker.data.network.dto.MemberDto
import com.saref.hrchecker.domain.entity.Event
import com.saref.hrchecker.domain.entity.Member
import java.text.SimpleDateFormat
import java.util.*

fun EventDto.convertToEvent() = Event(
    id = this.id,
    title = this.title,
    description = this.description,
    startDate = SimpleDateFormat(
        Constants.SERVER_DATE_FORMAT,
        Locale.US
    ).parse(this.date.start).time,
    endDate = SimpleDateFormat(Constants.SERVER_DATE_FORMAT, Locale.US).parse(this.date.end).time,
    cardImage = this.cardImage
)

fun MemberDto.convertToMember(eventId: Int) = Member(
    id = this.id,
    phone = this.phone,
    city = this.city,
    company = this.company,
    position = this.position,
    addition = this.addition,
    registeredDate = SimpleDateFormat(
        Constants.SERVER_DATE_FORMAT,
        Locale.US
    ).parse(this.registeredDate).time,
    agreedByManager = this.agreedByManager,
    lastName = this.lastName,
    firstName = this.firstName,
    patronymic = this.patronymic,
    email = this.email,
    eventId = eventId
)