package com.connect.android.client.model.common

import com.connect.android.client.model.profile.FieldsResponse
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CommonApi {

    @GET("/{tagName}")
    fun getTags(@Path("tagName") tagName: String): Single<Map<String, ArrayList<String>>>

    @GET("/goals")
    fun getGoals(): Maybe<FieldsResponse>
}