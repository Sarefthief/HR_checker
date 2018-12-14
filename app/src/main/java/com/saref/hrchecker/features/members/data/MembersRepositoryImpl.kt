package com.saref.hrchecker.features.members.data

import com.saref.hrchecker.data.network.RetrofitProvider
import com.saref.hrchecker.features.members.data.database.MembersDatabaseService
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import com.saref.hrchecker.features.members.data.network.dto.MemberPostResponse
import com.saref.hrchecker.features.members.domain.Member
import com.saref.hrchecker.features.members.domain.MembersRepository
import io.reactivex.Single
import retrofit2.Call

class MembersRepositoryImpl : MembersRepository
{
    private val databaseService = MembersDatabaseService()
    private val networkService = RetrofitProvider.membersApiService

    override fun getMembers(eventId: Int): Single<List<Member>> =
        databaseService.getMembers(eventId)

    override fun updateMember(member: Member) = databaseService.updateMember(member)

    override fun getPresentMembers(eventId: Int) = databaseService.getPresentMembers(eventId)

    override fun sendMembersToServer(
        eventId: Int,
        membersList: List<MemberPostDto>
    ): Call<MemberPostResponse> = networkService.sendMembers(eventId, membersList)


}