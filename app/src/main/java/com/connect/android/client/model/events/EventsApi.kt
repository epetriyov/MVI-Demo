package com.connect.android.client.model.events

import com.connect.android.client.model.common.ListData
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {
    @GET("/events")
    fun getEvents(): Maybe<ListData<Event>>

    @GET("/events/{eventId}/users")
    fun getEventMembers(@Path("eventId") eventId: String): Maybe<ListData<User>>

    @POST("/events/{eventId}/approve")
    fun approveEvent(@Path("eventId") eventId: String): Completable

    @POST("/events/{eventId}/decline")
    fun declineEvent(@Path("eventId") eventId: String): Completable
}