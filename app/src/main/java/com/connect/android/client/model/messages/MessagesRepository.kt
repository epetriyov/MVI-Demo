package com.connect.android.client.model.messages

import io.reactivex.Completable
import io.reactivex.Flowable

interface MessagesRepository {
    fun sendMessage(chatId: String, message: String): Completable

    fun loadChatMessages(chatId: String, offset: Int, limit: Int): Completable

    fun getMessages(chatId: String): Flowable<List<Message>>
}

class MessagesRepoImpl(
    private val messageDao: MessageDao,
    private val messagesApi: MessagesApi
) : MessagesRepository {
    override fun sendMessage(chatId: String, message: String): Completable {
        return messagesApi.sendMessage(chatId, MessageToSend(message))
    }

    override fun loadChatMessages(chatId: String, offset: Int, limit: Int): Completable {
        return messagesApi.getChatMessages(chatId, offset, limit).map { it.data }
            .flatMapCompletable { messageDao.insertMessages(it) }
    }

    override fun getMessages(chatId: String): Flowable<List<Message>> {
        return messageDao.getMessages(chatId).map { it.sortedByDescending { it.creationDate?.millis } }
    }
}