package com.connect.android.client.model.chats

import com.connect.android.client.model.common.ListData
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatsApi {

    @POST("/chats")
    fun createChat(@Body chatRequest: ChatRequest): Single<Chat>

    @GET("/users/me/chats/direct")
    fun getChats(@Query("offset") offset: Int, @Query("limit") limit: Int): Maybe<ListData<Chat>>
}