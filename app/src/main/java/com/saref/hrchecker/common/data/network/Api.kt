package com.saref.hrchecker.common.data.network

import com.saref.hrchecker.features.events.data.network.entity.EventDto
import com.saref.hrchecker.features.events.data.network.entity.MemberDto
import com.saref.hrchecker.features.members.data.network.entity.MemberPostResponse
import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api
{
    @GET("Events/registration")
    fun getEvents(): Single<List<EventDto>>

    @GET("Registration/members/event/{eventId}?token=cftteamtest2018")
    fun getMembers(@Path("eventId") eventId: Int): Single<List<MemberDto>>

    @POST("Registration/members/event/{eventId}/confirmation?token=cftteamtest2018")
    fun sendMembers(@Path("eventId") eventId: Int, @Body membersList: List<MemberPostDto>): Call<MemberPostResponse>
}
