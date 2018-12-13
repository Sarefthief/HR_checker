package com.saref.hrchecker.domain.entity

import io.reactivex.Single

interface EventsRepository
{
    fun getEventsFromDatabase(): Single<List<Event>>
    fun getEventsFromServer(): Single<List<Event>>
}