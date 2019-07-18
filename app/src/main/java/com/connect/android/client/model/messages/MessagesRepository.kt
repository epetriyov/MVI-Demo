package com.connect.android.client.model.messages

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

interface MessagesRepository {
    fun sendMessage(chatId: String, message: Message): Completable

    fun loadChatMessages(chatId: String, offset: Int, limit: Int): Maybe<List<Message>>

    fun getMessages(chatId: String): Flowable<List<Message>>
}

class MessagesRepoImpl(private val messageDao: MessageDao, private val messagesApi: MessagesApi) : MessagesRepository {
    override fun sendMessage(chatId: String, message: Message): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadChatMessages(chatId: String, offset: Int, limit: Int): Maybe<List<Message>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMessages(chatId: String): Flowable<List<Message>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}