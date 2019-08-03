package com.connect.android.client.model.chats

import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ChatsRepository {

    fun createChat(user: User): Single<Chat>

    fun getChats(): Flowable<List<Chat>>

    fun getChatsByText(searchText: String): List<Chat>

    fun updateChats(offset: Int, limit: Int): Completable
}

class ChatsRepoImpl(private val chatsApi: ChatsApi, private val chatDao: ChatDao) : ChatsRepository {
    override fun createChat(user: User): Single<Chat> {
        return chatsApi.createChat(ChatRequest(user = user))
    }

    override fun getChats(): Flowable<List<Chat>> {
        return chatDao.getChats()
    }

    override fun getChatsByText(searchText: String): List<Chat> {
        return chatDao.findChats(searchText)
    }

    override fun updateChats(offset: Int, limit: Int): Completable {
        return chatsApi.getChats(offset, limit)
            .map { it.data }
            .flatMapCompletable { chatDao.insertChats(it) }
    }

}