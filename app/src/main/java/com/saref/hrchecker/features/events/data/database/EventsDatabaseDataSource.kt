package com.saref.hrchecker.features.events.data.database

import com.saref.hrchecker.features.events.domain.entity.Event
import com.saref.hrchecker.features.members.domain.entity.Member
import io.reactivex.Single

interface EventsDatabaseDataSource
{
    fun saveEvents(events: List<Event>)

    fun saveMembers(members: List<Member>)

    fun getEvents(): Single<List<Event>>

    fun checkEvent(eventId: Int): Int

}