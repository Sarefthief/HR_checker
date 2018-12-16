package com.saref.hrchecker.features.events.data

import com.saref.hrchecker.App
import com.saref.hrchecker.features.events.domain.entity.Event
import com.saref.hrchecker.features.events.domain.EventsRepository
import io.reactivex.Observable
import io.reactivex.Single

class EventsRepositoryImpl : EventsRepository
{
    private val databaseDataSource = App.eventDatabaseDataSource
    private val networkDataSource = App.eventsNetworkDataSource

    override fun getEventsFromDatabase(): Single<List<Event>> =
        databaseDataSource.getEvents()

    override fun getEventsFromServer(): Single<List<Event>> =
        networkDataSource.getEvents().flatMap {
            Observable.fromIterable(it)
                .map { event ->
                    if(databaseDataSource.checkEvent(event.id) == 0)
                    {
                        networkDataSource.getMembers(event.id)
                            .map { membersList -> databaseDataSource.saveMembers(membersList) }
                            .subscribe()
                    }
                }
                .subscribe()

            databaseDataSource.saveEvents(it)
            databaseDataSource.getEvents()
        }.onErrorResumeNext(databaseDataSource.getEvents())


}