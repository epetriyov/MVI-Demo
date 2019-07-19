package com.connect.android.client.tools.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.joda.time.DateTime


class MoshiDateTimeConverter : JsonAdapter<DateTime>() {
    override fun fromJson(jsonReader: JsonReader): DateTime? {
        return DateTime(jsonReader.nextLong())
    }

    override fun toJson(writer: JsonWriter, value: DateTime?) {
        writer.value(value?.millis)
    }
}