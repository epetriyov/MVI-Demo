package com.connect.android.client.model.events

import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

interface EventsRepository {

    fun declineEvent(eventId: String): Completable

    fun approveEvent(eventId: String): Completable

    fun getEventMembers(eventId: String): Maybe<List<User>>

    fun loadEvents(): Flowable<List<Event>>

    fun updateEvents(): Completable
}

class EventsRepoImpl(private val eventDao: EventDao, private val eventsApi: EventsApi) : EventsRepository {
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

    override fun loadEvents(): Flowable<List<Event>> {
        return eventDao.getEvents()
    }

}