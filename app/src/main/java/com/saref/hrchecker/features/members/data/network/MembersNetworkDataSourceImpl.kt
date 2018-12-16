package com.saref.hrchecker.features.members.data.network

import com.saref.hrchecker.data.network.Api
import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.data.network.entity.MemberPostResponse
import retrofit2.Call

class MembersNetworkDataSourceImpl(private val api: Api): MembersNetworkDataSource
{
    override fun sendMembers(eventId: Int, membersList: List<MemberPostDto>): Call<MemberPostResponse> =
        api.sendMembers(eventId, membersList)
}