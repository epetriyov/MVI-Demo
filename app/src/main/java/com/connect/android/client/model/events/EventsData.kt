package com.connect.android.client.model.events

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by evgenii on 8/9/17.
 */
@Entity(tableName = "events")
data class Event(val point: List<Double>, val cost: Float, val closed: Boolean = false, @PrimaryKey val id: String? = null,
                 val startDate: Long, val finishDate: Long, @Ignore val settings: SettingsData, val picture: String,
                 var accept: Boolean, val description: String, val name: String, val place: String,
                 val address: String, val accepted: Int)

data class SettingsData(val FACEBOOK: String, val VKONTAKTE: String, val PHONE: String)