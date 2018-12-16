package com.saref.hrchecker.features.members.data.database

import com.saref.hrchecker.App
import com.saref.hrchecker.common.data.database.MemberDao
import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.domain.entity.Member
import com.saref.hrchecker.utils.convertToMemberPostDto
import io.reactivex.Observable
import io.reactivex.Single

class MembersDatabaseDataSourceImpl : MembersDatabaseDataSource
{
    private val memberDao: MemberDao = App.databaseProvider.memberDao()

    override fun getMembers(eventId: Int): Single<List<Member>> = memberDao.getMembers(eventId)

    override fun updateMember(member: Member) = memberDao.updateMember(member)

    override fun getPresentMembers(eventId: Int): Single<List<MemberPostDto>> = memberDao.getPresentMembers(eventId)
        .flatMap {
        Observable.fromIterable(it)
            .map { memberDto -> memberDto.convertToMemberPostDto() }
            .toList()
    }
}