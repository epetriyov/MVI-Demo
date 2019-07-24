package com.connect.android.client.tools.room

import androidx.room.TypeConverter
import com.connect.android.client.model.educations.EducationData
import com.connect.android.client.model.messages.Message
import com.connect.android.client.model.profile.ConnectionType
import com.connect.android.client.model.profile.Settings
import com.connect.android.client.model.profile.User
import com.connect.android.client.model.works.WorkData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.joda.time.DateTime


class ConnectConverters {

    val moshi = Moshi.Builder().build()

    val listStringAdapter =
        moshi.adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java))

    val listDoubleAdapter =
        moshi.adapter<List<Double>>(Types.newParameterizedType(List::class.java, Double::class.java))

    val listWorksAdapter =
        moshi.adapter<List<WorkData>>(Types.newParameterizedType(List::class.java, WorkData::class.java))

    val listEducationsAdapter =
        moshi.adapter<List<EducationData>>(Types.newParameterizedType(List::class.java, EducationData::class.java))

    val settingsTypeAdapter = moshi.adapter(Settings::class.java)

    val messageTypeAdapter = moshi.adapter(Message::class.java)

    val dateTimeTypeAdapter = moshi.adapter(DateTime::class.java)

    val userTypeAdapter = moshi.adapter(User::class.java)

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { listStringAdapter.toJson(value) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { listStringAdapter.fromJson(value) }
    }

    @TypeConverter
    fun fromDoubleList(value: List<Double>?): String {
        return value.let { listDoubleAdapter.toJson(value) }
    }

    @TypeConverter
    fun toDoubleList(value: String): List<Double>? {
        return listDoubleAdapter.fromJson(value)
    }

    @TypeConverter
    fun fromWorksList(value: List<WorkData>?): String? {
        return value?.let { listWorksAdapter.toJson(value) }
    }

    @TypeConverter
    fun toWorksList(value: String?): List<WorkData>? {
        return value?.let { listWorksAdapter.fromJson(value) }
    }

    @TypeConverter
    fun fromEducationsList(value: List<EducationData>?): String? {
        return value?.let { listEducationsAdapter.toJson(value) }
    }

    @TypeConverter
    fun toEducationsList(value: String?): List<EducationData>? {
        return value?.let { listEducationsAdapter.fromJson(value) }
    }

    @TypeConverter
    fun fromConnectionType(value: ConnectionType?): String? {
        return value?.let { value.name }
    }

    @TypeConverter
    fun toConnectionType(value: String?): ConnectionType? {
        return value?.let {
            try {
                ConnectionType.valueOf(value)
            } catch (e: Throwable) {
                null
            }
        }
    }

    @TypeConverter
    fun fromSettings(value: Settings?): String? {
        return value?.let { settingsTypeAdapter.toJson(it) }
    }

    @TypeConverter
    fun toSettings(value: String?): Settings? {
        return value?.let { settingsTypeAdapter.fromJson(it) }
    }

    @TypeConverter
    fun fromMessage(value: Message?): String? {
        return value?.let { messageTypeAdapter.toJson(it) }
    }

    @TypeConverter
    fun toMessage(value: String?): Message? {
        return value?.let { messageTypeAdapter.fromJson(it) }
    }

    @TypeConverter
    fun fromUser(value: User?): String? {
        return value?.let { userTypeAdapter.toJson(it) }
    }

    @TypeConverter
    fun toUser(value: String?): User? {
        return value?.let { userTypeAdapter.fromJson(it) }
    }

    @TypeConverter
    fun fromDateTime(value: DateTime?): String? {
        return value?.let { dateTimeTypeAdapter.toJson(it) }
    }

    @TypeConverter
    fun toDateTime(value: String?): DateTime? {
        return value?.let { dateTimeTypeAdapter.fromJson(it) }
    }
}