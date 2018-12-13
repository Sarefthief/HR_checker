package com.saref.hrchecker.data

import com.saref.hrchecker.data.database.MembersDatabaseService
import com.saref.hrchecker.domain.entity.Member
import com.saref.hrchecker.domain.entity.MembersRepository
import io.reactivex.Single

class MembersRepositoryImpl : MembersRepository
{
    private val databaseService = MembersDatabaseService()

    override fun getMembers(eventId: Int): Single<List<Member>> = databaseService.getMembers(eventId)


}