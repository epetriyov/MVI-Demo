package com.connect.android.client.model.chats

import com.connect.android.client.model.chat.Message
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface ChatsRepository {

    fun createChat(user: User): Single<Chat>

    fun getChats(): Flowable<List<Chat>>

    fun getChats(searchText: String): Flowable<List<Chat>>

    fun loadChats(offset: Int, limit: Int): Single<List<Chat>>

    fun sendMessage(chatId: String, message: Message): Completable

    fun loadChatMessages(chatId: String, offset: Int, limit: Int): Maybe<List<Message>>

    fun getMessages(chatId: String): Flowable<List<Message>>
}