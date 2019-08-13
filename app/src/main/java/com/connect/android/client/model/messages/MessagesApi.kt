package com.connect.android.client.model.messages

import com.connect.android.client.model.common.ListData
import io.reactivex.Completable
import io.reactivex.Maybe
import retrofit2.http.*

interface MessagesApi {
    @GET("/chats/{chatID}/messages")
    fun getChatMessages(
        @Path("chatID") chatId: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Maybe<ListData<Message>>

    @POST("/chats/{chatID}/messages")
    fun sendMessage(@Path("chatID") chatId: String, @Body message: MessageToSend): Completable
}