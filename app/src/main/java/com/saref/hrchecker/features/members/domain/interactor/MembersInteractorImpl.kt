package com.saref.hrchecker.features.members.domain.interactor

import com.saref.hrchecker.features.members.data.MembersRepositoryImpl
import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.data.network.entity.MemberPostResponse
import com.saref.hrchecker.features.members.domain.MembersRepository
import com.saref.hrchecker.features.members.domain.entity.Member
import io.reactivex.Single
import retrofit2.Call

class MembersInteractorImpl : MembersInteractor
{
    private var membersRepository: MembersRepository = MembersRepositoryImpl()

    override fun getMembers(eventId: Int): Single<List<Member>> =
        membersRepository.getMembers(eventId)

    override fun updateMember(member: Member) = membersRepository.updateMember(member)

    override fun getPresentMembers(eventId: Int): Single<List<MemberPostDto>> =
        membersRepository.getPresentMembers(eventId)

    override fun sendMembersToServer(
        eventId: Int,
        membersList: List<MemberPostDto>
    ): Call<MemberPostResponse> = membersRepository.sendMembersToServer(eventId, membersList)


}