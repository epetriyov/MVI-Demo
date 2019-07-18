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