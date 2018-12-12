package com.saref.hrchecker.data

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api
{
    @GET("Events/registration")
    fun getEvents(): Single<List<EventDto>>
}

class DataApiService(val api: Api)
{
    companion object
    {
        val dataApiService by lazy {
            DataApiService(create())
        }

        private fun create(): Api
        {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://beta-team.cft.ru/api/v1/")
                    .build()

            return retrofit.create(Api::class.java)
        }
    }

    fun getEvents(): Single<List<EventDto>>
    {
        return api.getEvents().flatMap {
            Observable.fromIterable(it)
                    .toList()
        }
    }
}