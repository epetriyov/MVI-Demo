package com.connect.android.client.model.chat

import com.connect.android.client.model.messages.Message
import io.reactivex.Flowable

interface ChatRepository {
    fun observeMessages(): Flowable<Message>
}

class ChatRepoImpl(private val chatApi: ChatApi) : ChatRepository {
    override fun observeMessages(): Flowable<Message> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}