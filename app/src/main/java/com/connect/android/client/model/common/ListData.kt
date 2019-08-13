package com.connect.android.client.model.common

import com.squareup.moshi.JsonClass

/**
 * Created by evgenii on 5/8/17.
 */
@JsonClass(generateAdapter = true)
data class ListData<T>(val totalCount: Int, val hasMore: Boolean, val data: List<T>)