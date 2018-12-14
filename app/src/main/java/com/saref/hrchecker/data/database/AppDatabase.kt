package com.saref.hrchecker.data.database

import android.arch.persistence.room.*
import com.saref.hrchecker.features.events.domain.Event
import com.saref.hrchecker.features.members.domain.Member
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

    @Query("SELECT EXISTS(SELECT 1 FROM events WHERE id = :id)")
    fun checkEvent(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEvents(events: List<Event>)
}

@Dao
interface MemberDao
{
    @Query("SELECT * FROM members WHERE eventId = :eventId order by lastName")
    fun getMembers(eventId: Int): Single<List<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMembers(members: List<Member>)

    @Update
    fun updateMember(member: Member)
}