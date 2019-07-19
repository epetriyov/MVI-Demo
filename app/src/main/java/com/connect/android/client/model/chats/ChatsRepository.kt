package com.connect.android.client.model.chats

import com.connect.android.client.model.chat.ChatApi
import com.connect.android.client.model.profile.User
import io.reactivex.Flowable
import io.reactivex.Single

interface ChatsRepository {

    fun createChat(user: User): Single<Chat>

    fun getChats(): Flowable<List<Chat>>

    fun getChats(searchText: String): Flowable<List<Chat>>

    fun loadChats(offset: Int, limit: Int): Single<List<Chat>>
}

class ChatsRepoImpl(private val chatApi: ChatApi, private val chatDao: ChatDao) : ChatsRepository {
    override fun createChat(user: User): Single<Chat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChats(): Flowable<List<Chat>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChats(searchText: String): Flowable<List<Chat>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadChats(offset: Int, limit: Int): Single<List<Chat>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}