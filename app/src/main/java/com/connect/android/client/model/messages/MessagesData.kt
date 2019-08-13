package com.connect.android.client.model.messages

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import org.joda.time.DateTime
import java.io.Serializable

@Entity(tableName = "messages")
@JsonClass(generateAdapter = true)
data class Message constructor(
    val text: String,
    val type: String? = null,
    val creationDate: DateTime,
    val userId: String,
    @PrimaryKey
    val id: String,
    val chatId: String
) : Serializable

@JsonClass(generateAdapter = true)
data class MessageToSend(val text: String)