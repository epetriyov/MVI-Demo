package com.connect.android.client.model.events

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(items: List<Event>): Completable

    @Delete
    fun deleteEvents(items: List<Event>): Completable

    @RawQuery(observedEntities = [Event::class])
    fun getEvents(query: SupportSQLiteQuery): Flowable<List<Event>>
}