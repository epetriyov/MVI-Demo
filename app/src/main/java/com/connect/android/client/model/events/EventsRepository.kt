package com.connect.android.client.model.events

import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

interface EventsRepository {

    fun declineEvent(eventId: String): Completable

    fun approveEvent(eventId: String): Completable

    fun getEventMembers(eventId: String): Maybe<List<User>>

    fun fetchEvents(): Flowable<List<Event>>
}

class EventsRepoImpl(private val eventDao: EventDao, private val eventsApi: EventsApi) : EventsRepository {
    override fun declineEvent(eventId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun approveEvent(eventId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEventMembers(eventId: String): Maybe<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchEvents(): Flowable<List<Event>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}