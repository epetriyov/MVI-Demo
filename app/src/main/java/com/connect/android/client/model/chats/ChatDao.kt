package com.connect.android.client.model.chats

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChats(items: List<Chat>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(item: Chat): Completable

    @Delete
    fun deleteChats(items: List<Chat>): Completable

    @Query("SELECT * FROM chats")
    fun getChats(): Maybe<List<Chat>>
}