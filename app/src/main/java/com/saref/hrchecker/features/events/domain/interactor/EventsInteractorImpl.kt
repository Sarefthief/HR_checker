package com.saref.hrchecker.features.events.domain.interactor

import com.saref.hrchecker.features.events.data.EventsRepositoryImpl
import com.saref.hrchecker.features.events.domain.EventsRepository
import com.saref.hrchecker.features.events.domain.entity.Event
import io.reactivex.Single

class EventsInteractorImpl : EventsInteractor
{
    private var eventsRepository: EventsRepository = EventsRepositoryImpl()

    override fun getEventsFromDatabase(): Single<List<Event>> = eventsRepository.getEventsFromDatabase()

    override fun getEventsFromServer(): Single<List<Event>> = eventsRepository.getEventsFromServer()

}