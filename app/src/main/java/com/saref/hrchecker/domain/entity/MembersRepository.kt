package com.saref.hrchecker.domain.entity

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface MembersRepository
{
    fun getMembers(eventId: Int): Single<List<Member>>

    fun updateMember(member: Member)
}