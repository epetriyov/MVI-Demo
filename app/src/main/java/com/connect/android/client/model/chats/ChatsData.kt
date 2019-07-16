package com.connect.android.client.model.chats

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.connect.android.client.model.profile.User
import com.google.gson.annotations.SerializedName

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey
    var id: String,
    var user: User,
    var lastMessage: Message,
    var creationDate: Long,
    var lastActiveDate: Long
)

data class ChatRequest(@SerializedName("object") val obj: String = "directChat", val user: User)

@Entity(tableName = "messages")
data class Message(
    var text: String,
    var type: String,
    var creationDate: Long,
    var userId: String,
    @PrimaryKey
    var id: String,
    var chatId: String
)