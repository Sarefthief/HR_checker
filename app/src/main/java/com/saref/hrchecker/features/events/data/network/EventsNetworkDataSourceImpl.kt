package com.saref.hrchecker.features.events.data.network

import com.saref.hrchecker.common.data.network.Api
import com.saref.hrchecker.features.events.domain.entity.Event
import com.saref.hrchecker.features.members.domain.entity.Member
import com.saref.hrchecker.utils.convertToEvent
import com.saref.hrchecker.utils.convertToMember
import io.reactivex.Observable
import io.reactivex.Single

class EventsNetworkDataSourceImpl(private val api: Api) : EventsNetworkDataSource
{
    override fun getEvents(): Single<List<Event>> =
        api.getEvents().flatMap {
            Observable.fromIterable(it)
                .map { eventDto -> eventDto.convertToEvent() }
                .toList()
        }

    override fun getMembers(eventId: Int): Single<List<Member>> =
        api.getMembers(eventId).flatMap {
            Observable.fromIterable(it)
                .map { memberDto -> memberDto.convertToMember(eventId) }
                .toList()
        }
}