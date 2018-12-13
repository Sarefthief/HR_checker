package com.saref.hrchecker.data.network

import com.saref.hrchecker.domain.entity.Event
import com.saref.hrchecker.domain.entity.Member
import com.saref.hrchecker.utils.convertToEvent
import com.saref.hrchecker.utils.convertToMember
import io.reactivex.Observable
import io.reactivex.Single

class DataApiService(private val api: Api)
{
    fun getEvents(): Single<List<Event>> =
        api.getEvents().flatMap {
            Observable.fromIterable(it)
                .map { eventDto -> eventDto.convertToEvent() }
                .toList()
        }

    fun getMembers(eventId: Int): Single<List<Member>> =
        api.getMembers(eventId).flatMap {
            Observable.fromIterable(it)
                .map { memberDto -> memberDto.convertToMember(eventId) }
                .toList()
        }

}