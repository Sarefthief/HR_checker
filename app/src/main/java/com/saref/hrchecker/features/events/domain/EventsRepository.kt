package com.saref.hrchecker.features.events.domain

import io.reactivex.Single

interface EventsRepository
{
    fun getEventsFromDatabase(): Single<List<Event>>
    fun getEventsFromServer(): Single<List<Event>>
}