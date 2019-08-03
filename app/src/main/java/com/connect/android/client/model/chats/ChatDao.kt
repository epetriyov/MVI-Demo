package com.connect.android.client.model.chats

import androidx.room.*
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
    fun getChats(): Flowable<List<Chat>>

    @Query("SELECT * FROM chats where msg_text GLOB '*' || :query|| '*'")
    fun findChats(query: String): List<Chat>
}