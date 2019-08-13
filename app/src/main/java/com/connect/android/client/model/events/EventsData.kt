package com.connect.android.client.model.events

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import org.joda.time.DateTime
import java.io.Serializable

/**
 * Created by evgenii on 8/9/17.
 */
@Entity(tableName = "events")
@JsonClass(generateAdapter = true)
data class Event @JvmOverloads constructor(
    @PrimaryKey val id: String,
    val point: List<Double>,
    val cost: Float?,
    val closed: Boolean,
    val startDate: DateTime,
    val finishDate: DateTime,
    @Ignore val settings: SettingsData? = null,
    val picture: String,
    val accept: Boolean,
    val description: String,
    val name: String,
    val place: String,
    val address: String,
    val accepted: Int
): Serializable

data class SettingsData(val FACEBOOK: String, val VKONTAKTE: String, val PHONE: String): Serializable