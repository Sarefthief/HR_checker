package com.saref.hrchecker.data.database

import com.saref.hrchecker.App
import com.saref.hrchecker.domain.entity.Event
import com.saref.hrchecker.domain.entity.Member
import io.reactivex.Single

class EventsDatabaseService
{
    private val eventDao: EventDao = App.instance.getEventDao()
    private val memberDao: MemberDao = App.instance.getMemberDao()

    fun saveEvents(events: List<Event>) = eventDao.insertAllEvents(events)

    fun saveMembers(members: List<Member>) = memberDao.insertAllMembers(members)

    fun getEvents(): Single<List<Event>> = eventDao.getEventList()

    fun checkEvent(eventId: Int) = eventDao.checkEvent(eventId)
}

