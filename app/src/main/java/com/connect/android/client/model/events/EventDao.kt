package com.connect.android.client.model.events

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(items: List<Event>): Completable

    @Delete
    fun deleteEvents(items: List<Event>): Completable

    @Query("SELECT * FROM events")
    fun getEvents(): Flowable<List<Event>>
}