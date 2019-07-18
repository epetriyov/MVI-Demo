package com.connect.android.client.model.common

/**
 * Created by evgenii on 5/8/17.
 */
data class ListData<T>(val totalCount: Int, val hasMore: Boolean, val data: List<T>)