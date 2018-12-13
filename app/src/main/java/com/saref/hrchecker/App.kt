package com.saref.hrchecker

import android.app.Application
import android.arch.persistence.room.Room
import com.saref.hrchecker.data.database.AppDatabase
import com.saref.hrchecker.data.database.EventDao
import com.saref.hrchecker.data.database.MemberDao

class App : Application()
{
    companion object
    {
        lateinit var instance: App
            private set
    }

    private lateinit var databaseProvider: AppDatabase

    override fun onCreate()
    {
        super.onCreate()
        instance = this
        databaseProvider = Room.databaseBuilder(this, AppDatabase::class.java, "hr-database").build()
    }

    fun getEventDao(): EventDao = databaseProvider.eventDao()

    fun getMemberDao(): MemberDao = databaseProvider.memberDao()
}
