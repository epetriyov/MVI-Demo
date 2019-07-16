package com.connect.android.client.model.chats

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessages(items: List<Message>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(item: Message): Completable

    @Delete
    fun deleteMessages(items: List<Message>): Completable

    @Query("SELECT * FROM messages")
    fun getMessages(): Maybe<List<Message>>
}