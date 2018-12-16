package com.saref.hrchecker.features.events.domain.interactor

import com.saref.hrchecker.features.events.domain.entity.Event
import io.reactivex.Single

interface EventsInteractor
{
    fun getEventsFromDatabase(): Single<List<Event>>
    fun getEventsFromServer(): Single<List<Event>>
}