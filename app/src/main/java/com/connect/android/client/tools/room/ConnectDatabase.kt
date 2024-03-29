package com.connect.android.client.tools.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.connect.android.client.model.chats.Chat
import com.connect.android.client.model.chats.ChatDao
import com.connect.android.client.model.contacts.ContactsDao
import com.connect.android.client.model.events.Event
import com.connect.android.client.model.events.EventDao
import com.connect.android.client.model.messages.Message
import com.connect.android.client.model.messages.MessageDao
import com.connect.android.client.model.profile.Me
import com.connect.android.client.model.profile.ProfileDao
import com.connect.android.client.model.profile.User

@Database(
    entities = [Chat::class, Event::class, User::class, Me::class, Message::class],
    version = 1
)
@TypeConverters(ConnectConverters::class)
abstract class ConnectDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun eventDao(): EventDao
    abstract fun profileDao(): ProfileDao

}