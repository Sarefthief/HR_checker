package com.saref.hrchecker.features.members.data

import com.saref.hrchecker.features.members.data.database.MembersDatabaseService
import com.saref.hrchecker.features.members.domain.Member
import com.saref.hrchecker.features.members.domain.MembersRepository
import io.reactivex.Single

class MembersRepositoryImpl : MembersRepository
{
    private val databaseService = MembersDatabaseService()

    override fun getMembers(eventId: Int): Single<List<Member>> =
        databaseService.getMembers(eventId)

    override fun updateMember(member: Member) = databaseService.updateMember(member)

}