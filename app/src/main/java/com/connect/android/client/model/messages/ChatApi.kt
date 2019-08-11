package com.connect.android.client.model.messages

import com.tinder.scarlet.Event
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface ChatApi {

    @Receive
    fun observeWebSocketEvent(): Flowable<Event>

    @Send
    fun sendMessage(message: MessageToSend)

    @Receive
    fun observeMessages(): Flowable<Message>
}