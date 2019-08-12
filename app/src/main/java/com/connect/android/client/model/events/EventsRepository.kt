package com.connect.android.client.model.events

import androidx.sqlite.db.SimpleSQLiteQuery
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface EventsRepository {

    fun declineEvent(eventId: String): Completable

    fun approveEvent(eventId: String): Completable

    fun loadEventMembers(query: String? = null): Observable<List<User>>

    fun fetchEventMembers(eventId: String): Completable

    fun loadEvents(query: String? = null, accepted: Boolean? = null): Flowable<List<Event>>

    fun updateEvents(): Completable
}

class EventsRepoImpl(
    private val eventDao: EventDao,
    private val eventMembersStorage: EventMembersStorage,
    private val eventsApi: EventsApi
) : EventsRepository {

    override fun loadEvents(query: String?, accepted: Boolean?): Flowable<List<Event>> {
        val sql = "SELECT * FROM events" + (if (!query.isNullOrEmpty()) " WHERE UPPER(name) GLOB UPPER('*$query*')" else "") +
                (accepted?.let { if (query.isNullOrEmpty()) " WHERE accepted = 1" else " AND accepted = 1" } ?: "") + ""
        val args =
//            if (!query.isNullOrEmpty()) arrayOf(query) else
            emptyArray<Any>()
        val sqlLiteQuery = SimpleSQLiteQuery(sql, args)
        return eventDao.getEvents(sqlLiteQuery)
    }

    override fun updateEvents(): Completable {
        return eventsApi.getEvents().flatMapCompletable {
            eventDao.deleteEvents(eventDao.getAllEvents())
                .andThen(eventDao.insertEvents(it.data))
        }
    }

    override fun declineEvent(eventId: String): Completable {
        return eventsApi.declineEvent(eventId)
    }

    override fun approveEvent(eventId: String): Completable {
        return eventsApi.approveEvent(eventId)
    }

    override fun loadEventMembers(query: String?): Observable<List<User>> {
        return eventMembersStorage.getEventMembers(query)
    }

    override fun fetchEventMembers(eventId: String): Completable {
        return eventsApi.getEventMembers(eventId).map { it.data }
            .flatMapCompletable { eventMembersStorage.updateEventMembers(it) }
    }
}