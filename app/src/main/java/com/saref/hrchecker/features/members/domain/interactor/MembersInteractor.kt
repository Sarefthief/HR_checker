package com.saref.hrchecker.features.members.domain.interactor

import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.data.network.entity.MemberPostResponse
import com.saref.hrchecker.features.members.domain.entity.Member
import io.reactivex.Single
import retrofit2.Call

interface MembersInteractor
{
    fun getMembers(eventId: Int): Single<List<Member>>

    fun updateMember(member: Member)

    fun getPresentMembers(eventId: Int): Single<List<MemberPostDto>>

    fun sendMembersToServer(eventId: Int, membersList: List<MemberPostDto>): Call<MemberPostResponse>
}