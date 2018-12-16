package com.saref.hrchecker.features.events.data.database

import com.saref.hrchecker.App
import com.saref.hrchecker.data.database.EventDao
import com.saref.hrchecker.data.database.MemberDao
import com.saref.hrchecker.features.events.domain.entity.Event
import com.saref.hrchecker.features.members.domain.entity.Member
import io.reactivex.Single

class EventsDatabaseDataSourceImpl: EventsDatabaseDataSource
{
    private val eventDao: EventDao = App.databaseProvider.eventDao()
    private val memberDao: MemberDao = App.databaseProvider.memberDao()

    override fun saveEvents(events: List<Event>) = eventDao.insertAllEvents(events)

    override fun saveMembers(members: List<Member>) = memberDao.insertAllMembers(members)

    override fun getEvents(): Single<List<Event>> = eventDao.getEventList()

    override fun checkEvent(eventId: Int): Int = eventDao.checkEvent(eventId)
}

