package com.connect.android.client.model.works

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface WorksApi {
    @DELETE("/works/{id}")
    fun removeWork(@Path("id") workId: String): Completable

    @POST("/works/{id}")
    fun updateWork(@Path("id") workId: String, @Body work: WorkData): Completable

    @POST("/works")
    fun addWork(@Body work: WorkData): Completable
}