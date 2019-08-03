package com.connect.android.client.model.messages

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessages(items: List<Message>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(item: Message): Completable

    @Delete
    fun deleteMessages(items: List<Message>): Completable

    @Query("SELECT * FROM messages WHERE chatId = :chatId")
    fun getMessages(chatId: String): Flowable<List<Message>>
}