package com.saref.hrchecker.features.members.domain

import io.reactivex.Single

interface MembersRepository
{
    fun getMembers(eventId: Int): Single<List<Member>>

    fun updateMember(member: Member)
}