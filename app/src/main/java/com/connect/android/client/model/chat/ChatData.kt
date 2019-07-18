package com.connect.android.client.model.chat

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by evgenii on 7/29/17.
 */
data class ChatData(val chatId: String?, val text: String?, val userId: String) : Serializable

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