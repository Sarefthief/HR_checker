package com.saref.hrchecker.features.members.data

import com.saref.hrchecker.App
import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.data.network.entity.MemberPostResponse
import com.saref.hrchecker.features.members.domain.entity.Member
import com.saref.hrchecker.features.members.domain.MembersRepository
import io.reactivex.Single
import retrofit2.Call

class MembersRepositoryImpl : MembersRepository
{
    private val databaseDataSource = App.membersDatabaseDataSource
    private val networkDataSource = App.membersNetworkDataSource

    override fun getMembers(eventId: Int): Single<List<Member>> =
        databaseDataSource.getMembers(eventId)

    override fun updateMember(member: Member) = databaseDataSource.updateMember(member)

    override fun getPresentMembers(eventId: Int) = databaseDataSource.getPresentMembers(eventId)

    override fun sendMembersToServer(
        eventId: Int,
        membersList: List<MemberPostDto>
    ): Call<MemberPostResponse> = networkDataSource.sendMembers(eventId, membersList)


}