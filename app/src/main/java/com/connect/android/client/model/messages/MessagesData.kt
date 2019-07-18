package com.connect.android.client.model.messages

import androidx.room.Entity
import androidx.room.PrimaryKey

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