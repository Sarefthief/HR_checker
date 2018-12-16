package com.saref.hrchecker.features.members.data.network

import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.data.network.entity.MemberPostResponse
import retrofit2.Call

interface MembersNetworkDataSource
{
    fun sendMembers(eventId: Int, membersList: List<MemberPostDto>): Call<MemberPostResponse>
}