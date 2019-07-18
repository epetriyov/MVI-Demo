package com.connect.android.client.model.location

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LocationApi {
    @POST("/users/me/location")
    fun sendLocation(@Body location: LocationData): Completable

    @GET("/users/me/location")
    fun getLocation(): Single<Response<LocationData>>
}