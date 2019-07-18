package com.connect.android.client.model.chats

import com.connect.android.client.model.ListData
import com.connect.android.client.model.chat.Message
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ChatsApi {
    @GET("/chats/{chatID}/messages")
    fun getChatMessages(
        @Path("chatID") chatId: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<Response<ListData<Message>>>

    @POST("/chats/{chatID}/messages")
    fun sendMessage(@Path("chatID") chatId: String, @Body message: Message): Single<Message>

    @POST("/chats")
    fun createChat(@Body chatRequest: ChatRequest): Single<Chat>

    @GET("/users/me/chats/direct")
    fun getChats(@Query("offset") offset: Int, @Query("limit") limit: Int): Maybe<ListData<Chat>>
}