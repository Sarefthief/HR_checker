package com.saref.hrchecker.features.events.data.network

import com.saref.hrchecker.features.events.domain.entity.Event
import com.saref.hrchecker.features.members.domain.entity.Member
import io.reactivex.Single

interface EventsNetworkDataSource
{
    fun getEvents(): Single<List<Event>>

    fun getMembers(eventId: Int): Single<List<Member>>
}