package com.saref.hrchecker.features.members.data.database

import com.saref.hrchecker.App
import com.saref.hrchecker.data.database.MemberDao
import com.saref.hrchecker.features.members.domain.Member
import io.reactivex.Single

class MembersDatabaseService
{
    private val memberDao: MemberDao = App.instance.getMemberDao()

    fun getMembers(eventId: Int): Single<List<Member>> = memberDao.getMembers(eventId)

    fun updateMember(member: Member) = memberDao.updateMember(member)
}