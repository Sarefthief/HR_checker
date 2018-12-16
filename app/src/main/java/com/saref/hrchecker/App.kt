package com.saref.hrchecker

import android.app.Application
import android.arch.persistence.room.Room
import com.saref.hrchecker.common.data.database.AppDatabase
import com.saref.hrchecker.common.data.network.Api
import com.saref.hrchecker.features.events.data.database.EventsDatabaseDataSource
import com.saref.hrchecker.features.events.data.database.EventsDatabaseDataSourceImpl
import com.saref.hrchecker.features.events.data.network.EventsNetworkDataSource
import com.saref.hrchecker.features.events.data.network.EventsNetworkDataSourceImpl
import com.saref.hrchecker.features.members.data.database.MembersDatabaseDataSource
import com.saref.hrchecker.features.members.data.database.MembersDatabaseDataSourceImpl
import com.saref.hrchecker.features.members.data.network.MembersNetworkDataSource
import com.saref.hrchecker.features.members.data.network.MembersNetworkDataSourceImpl
import com.saref.hrchecker.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application()
{
    companion object
    {
        lateinit var instance: App
            private set

        lateinit var databaseProvider: AppDatabase

        lateinit var eventsNetworkDataSource: EventsNetworkDataSource

        lateinit var eventDatabaseDataSource: EventsDatabaseDataSource

        lateinit var membersNetworkDataSource: MembersNetworkDataSource

        lateinit var membersDatabaseDataSource: MembersDatabaseDataSource
    }

    override fun onCreate()
    {
        super.onCreate()
        instance = this
        databaseProvider = Room.databaseBuilder(this, AppDatabase::class.java, "hr-database").build()

        val retrofitApi: Api = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(Api::class.java)

        eventsNetworkDataSource = EventsNetworkDataSourceImpl(retrofitApi)
        eventDatabaseDataSource = EventsDatabaseDataSourceImpl()
        membersNetworkDataSource = MembersNetworkDataSourceImpl(retrofitApi)
        membersDatabaseDataSource = MembersDatabaseDataSourceImpl()
    }

}
