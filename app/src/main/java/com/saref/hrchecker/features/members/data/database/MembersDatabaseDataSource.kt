package com.saref.hrchecker.features.members.data.database

import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.domain.entity.Member
import io.reactivex.Single

interface MembersDatabaseDataSource
{
    fun getMembers(eventId: Int): Single<List<Member>>

    fun updateMember(member: Member)

    fun getPresentMembers(eventId: Int): Single<List<MemberPostDto>>
}