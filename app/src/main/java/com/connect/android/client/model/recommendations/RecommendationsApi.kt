package com.connect.android.client.model.recommendations

import com.connect.android.client.model.common.ListData
import com.connect.android.client.model.profile.ConnectResponse
import com.connect.android.client.model.profile.User
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecommendationsApi {
    @GET("/users/recommended")
    fun getRecommendations(): Maybe<ListData<User>>

    @POST("/users/{user_id}/connect")
    fun connectUser(@Path("user_id") userId: String): Single<ConnectResponse>

    @POST("/users/{user_id}/decline")
    fun declineUser(@Path("user_id") userId: String): Single<ConnectResponse>
}