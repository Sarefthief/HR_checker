package com.saref.hrchecker.features.members.domain

import com.saref.hrchecker.features.members.data.network.dto.MemberPostResponse
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import io.reactivex.Single
import retrofit2.Call

interface MembersRepository
{
    fun getMembers(eventId: Int): Single<List<Member>>

    fun updateMember(member: Member)

    fun getPresentMembers(eventId: Int): Single<List<MemberPostDto>>

    fun sendMembersToServer(eventId: Int, membersList: List<MemberPostDto>): Call<MemberPostResponse>
}