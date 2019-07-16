package com.connect.android.client.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.connect.android.client.model.contacts.ContactsDao
import com.connect.android.client.model.chats.Chat
import com.connect.android.client.model.chats.ChatDao
import com.connect.android.client.model.chats.Message
import com.connect.android.client.model.chats.MessageDao
import com.connect.android.client.model.events.Event
import com.connect.android.client.model.events.EventDao
import com.connect.android.client.model.profile.User

@Database(
    entities = [Chat::class, Event::class, User::class, Message::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun eventDao(): EventDao

}