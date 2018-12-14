package com.saref.hrchecker.features.members.data.network

import com.saref.hrchecker.data.network.Api
import com.saref.hrchecker.features.members.data.network.dto.MemberListDto
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import retrofit2.Call

class MembersApiService(private val api: Api)
{
    fun sendMembers(eventId: Int, membersList: MemberListDto): Call<MemberListDto> =
        api.sendMembers(eventId, membersList)
}