package com.saref.hrchecker.data

import com.saref.hrchecker.data.database.EventsDatabaseService
import com.saref.hrchecker.data.network.RetrofitProvider
import com.saref.hrchecker.domain.entity.Event
import com.saref.hrchecker.domain.entity.EventsRepository
import io.reactivex.Observable
import io.reactivex.Single

class EventsRepositoryImpl : EventsRepository
{


    private val databaseService = EventsDatabaseService()
    private val networkService = RetrofitProvider.dataApiService

    override fun getEventsFromDatabase(): Single<List<Event>> =
        databaseService.getEvents()

    override fun getEventsFromServer(): Single<List<Event>> =
        networkService.getEvents().flatMap {
            Observable.fromIterable(it)
                .map { event ->
                    if(databaseService.checkEvent(event.id) == 0)
                    {
                        networkService.getMembers(event.id)
                            .map { membersList -> databaseService.saveMembers(membersList) }
                            .subscribe()
                    }
                }
                .subscribe()

            databaseService.saveEvents(it)
            databaseService.getEvents()
        }.onErrorResumeNext(databaseService.getEvents())
}