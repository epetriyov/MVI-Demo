package com.connect.android.client.tools.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import org.joda.time.DateTime

class MoshiDateTimeConverter {
    @FromJson
    fun fromJson(jsonReader: JsonReader): DateTime? {
        return DateTime(jsonReader.nextLong())
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: DateTime?) {
        writer.value(value?.millis)
    }
}