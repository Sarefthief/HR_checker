package com.saref.hrchecker.features.members.data

import com.saref.hrchecker.data.network.RetrofitProvider
import com.saref.hrchecker.features.members.data.database.MembersDatabaseService
import com.saref.hrchecker.features.members.data.network.dto.MemberListDto
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import com.saref.hrchecker.features.members.domain.Member
import com.saref.hrchecker.features.members.domain.MembersRepository
import com.saref.hrchecker.utils.convertToMemberPostDto
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
        membersList: MemberListDto
    ): Call<MemberListDto> = networkService.sendMembers(eventId, membersList)


}