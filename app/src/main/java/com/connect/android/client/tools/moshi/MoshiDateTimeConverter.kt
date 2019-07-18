package com.connect.android.client.tools.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter


class MoshiDateTimeConverter(private val fmt: DateTimeFormatter) : JsonAdapter<DateTime>() {
    override fun fromJson(jsonReader: JsonReader): DateTime? {
        val nextString = jsonReader.nextString()
        if (nextString.isNullOrEmpty()) {
            return null
        }
        return try {
            fmt.parseDateTime(nextString)
        } catch (e: IllegalArgumentException) {
            println("${e.message}, Right format ${fmt.print(DateTime.now())}")
            return null
        }

    }

    override fun toJson(writer: JsonWriter, value: DateTime?) {
        writer.value(value?.toString(fmt) ?: "")
    }
}