package com.connect.android.client.model.chats

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.connect.android.client.model.messages.Message
import com.connect.android.client.model.profile.User
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

@Entity(tableName = "chats")
data class Chat @JvmOverloads constructor(
    @PrimaryKey
    val id: String,
    val user: User,
    @Embedded(prefix = "msg_")
    val lastMessage: Message? = null,
    val creationDate: DateTime,
    val lastActiveDate: DateTime
)

data class ChatRequest(@SerializedName("object") val obj: String = "directChat", val user: User)