package com.saref.hrchecker.data.database

import android.arch.persistence.room.*
import com.saref.hrchecker.domain.entity.Event
import com.saref.hrchecker.domain.entity.Member
import io.reactivex.Single

@Database(entities = [Event::class, Member::class], version = 2)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun eventDao(): EventDao

    abstract fun memberDao(): MemberDao
}

@Dao
interface EventDao
{
    @Query("SELECT * FROM events")
    fun getEventList(): Single<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEvents(events: List<Event>)
}

@Dao
interface MemberDao
{
    @Query("SELECT * FROM members WHERE eventId = :eventId")
    fun getMembers(eventId: Int): Single<List<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMembers(members: List<Member>)
}