package com.connect.android.client.model.messages

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.io.Serializable

@Entity(tableName = "messages")
data class Message constructor(
    val text: String,
    val type: String,
    val creationDate: DateTime,
    val userId: String,
    @PrimaryKey
    val id: String,
    val chatId: String
) : Serializable

data class MessageToSend(val text: String)