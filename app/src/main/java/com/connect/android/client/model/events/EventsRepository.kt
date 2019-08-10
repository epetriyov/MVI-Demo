package com.connect.android.client.model.events

import androidx.sqlite.db.SimpleSQLiteQuery
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

interface EventsRepository {

    fun declineEvent(eventId: String): Completable

    fun approveEvent(eventId: String): Completable

    fun getEventMembers(eventId: String): Maybe<List<User>>

    fun loadEvents(query: String? = null, accepted: Boolean? = null): Flowable<List<Event>>

    fun updateEvents(): Completable
}

class EventsRepoImpl(private val eventDao: EventDao, private val eventsApi: EventsApi) : EventsRepository {
    override fun loadEvents(query: String?, accepted: Boolean?): Flowable<List<Event>> {
        val sql = "SELECT * FROM events" + (query?.let { " WHERE name GLOB '*' || :query|| '*'" }
            ?: "") + (accepted?.let { if (query == null) " WHERE accepted = 1" else " AND accepted = 1" })
        val args = when {
            query != null && accepted != null -> arrayOf(query, accepted)
            query != null && accepted == null -> arrayOf(query)
            query == null && accepted != null -> arrayOf(accepted)
            else -> emptyArray()
        }
        val sqlLiteQuery = SimpleSQLiteQuery(sql, args)
        return eventDao.getEvents(sqlLiteQuery)
    }

    override fun updateEvents(): Completable {
        return eventsApi.getEvents().flatMapCompletable { eventDao.insertEvents(it.data) }
    }

    override fun declineEvent(eventId: String): Completable {
        return eventsApi.declineEvent(eventId)
    }

    override fun approveEvent(eventId: String): Completable {
        return eventsApi.approveEvent(eventId)
    }

    override fun getEventMembers(eventId: String): Maybe<List<User>> {
        return eventsApi.getEventMembers(eventId).map { it.data }
    }
}