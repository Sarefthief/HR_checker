package com.saref.hrchecker.features.events.data.network

import com.saref.hrchecker.features.events.data.network.dto.EventDto
import com.saref.hrchecker.features.events.data.network.dto.MemberDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api
{
    @GET("Events/registration")
    fun getEvents(): Single<List<EventDto>>

    @GET("Registration/members/event/{eventId}?token=cftteamtest2018")
    fun getMembers(@Path("eventId") eventId: Int): Single<List<MemberDto>>
}
