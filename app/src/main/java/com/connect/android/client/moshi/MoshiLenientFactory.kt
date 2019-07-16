package com.connect.android.client.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type


val LENIENT_FACTORY: JsonAdapter.Factory = object : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<*> {
        return moshi.nextAdapter<Any>(this, type, annotations).lenient()
    }
}