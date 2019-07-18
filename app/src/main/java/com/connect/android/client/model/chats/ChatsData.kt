package com.connect.android.client.model.chats

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.connect.android.client.model.chat.Message
import com.connect.android.client.model.profile.User
import com.google.gson.annotations.SerializedName

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey
    var id: String,
    var user: User,
    @Embedded(prefix = "msg_")
    var lastMessage: Message,
    var creationDate: Long,
    var lastActiveDate: Long
)

data class ChatRequest(@SerializedName("object") val obj: String = "directChat", val user: User)