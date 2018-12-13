package com.saref.hrchecker.domain.entity

import io.reactivex.Single

interface EventsRepository
{
    fun getEvents(): Single<List<Event>>
}