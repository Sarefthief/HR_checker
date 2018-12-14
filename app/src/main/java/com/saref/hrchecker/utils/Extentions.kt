package com.saref.hrchecker.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.saref.hrchecker.features.events.data.network.dto.EventDto
import com.saref.hrchecker.features.events.data.network.dto.MemberDto
import com.saref.hrchecker.features.events.domain.Event
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import com.saref.hrchecker.features.members.domain.Member
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

fun Member.convertToMemberPostDto() =
    MemberPostDto(
        id = this.id,
        isVisited = this.presentStatus,
        visitedDate = this.visitedDate
    )

fun String.setTextHTML(): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    }
    else
    {
        Html.fromHtml(this)
    }
