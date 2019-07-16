package com.connect.android.client.model

/**
 * Created by evgenii on 5/8/17.
 */
data class ListData<T>(val totalCount: Int, val hasMore: Boolean, val data: List<T>)