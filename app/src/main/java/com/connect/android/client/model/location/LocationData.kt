package com.connect.android.client.model.location

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationData(val point: List<Double>)