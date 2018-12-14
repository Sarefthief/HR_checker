package com.saref.hrchecker.features.members.data.network

import com.saref.hrchecker.data.network.Api
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import com.saref.hrchecker.features.members.data.network.dto.MemberPostResponse
import retrofit2.Call

class MembersApiService(private val api: Api)
{
    fun sendMembers(eventId: Int, membersList: List<MemberPostDto>): Call<MemberPostResponse> =
        api.sendMembers(eventId, membersList)
}