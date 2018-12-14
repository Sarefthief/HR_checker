package com.saref.hrchecker.data.network

import com.saref.hrchecker.features.events.data.network.EventsApiService
import com.saref.hrchecker.features.members.data.network.MembersApiService
import com.saref.hrchecker.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider
{
    companion object
    {
        val eventsApiService by lazy {
            EventsApiService(create())
        }

        val membersApiService by lazy {
            MembersApiService(create())
        }

        private fun create(): Api = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(Api::class.java)
    }
}