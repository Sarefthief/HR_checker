package com.saref.hrchecker.domain.entity

import io.reactivex.Single

interface MembersRepository
{
    fun getMembers(eventId: Int): Single<List<Member>>
}