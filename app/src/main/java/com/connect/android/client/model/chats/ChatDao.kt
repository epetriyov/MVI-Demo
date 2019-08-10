package com.connect.android.client.model.chats

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChats(items: List<Chat>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(item: Chat): Completable

    @Delete
    fun deleteChats(items: List<Chat>): Completable

    @Query("SELECT * FROM chats")
    fun getAllChats(): List<Chat>

    @RawQuery(observedEntities = [Chat::class])
    fun getChats(query: SupportSQLiteQuery): Flowable<List<Chat>>
}