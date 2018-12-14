package com.saref.hrchecker.features.members.data.database

import com.saref.hrchecker.App
import com.saref.hrchecker.data.database.MemberDao
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import com.saref.hrchecker.features.members.domain.Member
import com.saref.hrchecker.utils.convertToMember
import com.saref.hrchecker.utils.convertToMemberPostDto
import io.reactivex.Observable
import io.reactivex.Single

class MembersDatabaseService
{
    private val memberDao: MemberDao = App.instance.getMemberDao()

    fun getMembers(eventId: Int): Single<List<Member>> = memberDao.getMembers(eventId)

    fun updateMember(member: Member) = memberDao.updateMember(member)

    fun getPresentMembers(eventId: Int): Single<List<MemberPostDto>> = memberDao.getPresentMembers(eventId)
        .flatMap {
        Observable.fromIterable(it)
            .map { memberDto -> memberDto.convertToMemberPostDto() }
            .toList()
    }
}