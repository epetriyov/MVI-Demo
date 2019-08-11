package com.connect.android.client.model.chats

import androidx.sqlite.db.SimpleSQLiteQuery
import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ChatsRepository {

    fun createChat(user: User): Single<Chat>

    fun getChats(searchText: String? = null): Flowable<List<Chat>>

    fun updateChats(): Completable
}

class ChatsRepoImpl(private val chatsApi: ChatsApi, private val chatDao: ChatDao) : ChatsRepository {

    companion object {
        private const val CHATS_LIMIT = 300
    }

    override fun createChat(user: User): Single<Chat> {
        return chatsApi.createChat(ChatRequest(user = User(id = user.id, name = user.name)))
    }

    override fun getChats(searchText: String?): Flowable<List<Chat>> {
        val sql = "SELECT * FROM chats" + if (searchText.isNullOrEmpty()) ""
        else " WHERE msg_text GLOB '*' || :query|| '*' OR usr_name  GLOB '*' || :query|| '*'"
        val args = if (searchText.isNullOrEmpty()) emptyArray() else arrayOf(searchText)
        val sqlLiteQuery = SimpleSQLiteQuery(sql, args)
        return chatDao.getChats(sqlLiteQuery)
    }

    override fun updateChats(): Completable {
        return chatsApi.getChats(0, CHATS_LIMIT)
            .map { it.data }
            .flatMapCompletable { items ->
                chatDao.deleteChats(chatDao.getAllChats())
                    .andThen(chatDao.insertChats(items))
            }
    }

}