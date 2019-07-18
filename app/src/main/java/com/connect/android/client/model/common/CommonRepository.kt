package com.connect.android.client.model.common

import io.reactivex.Maybe
import io.reactivex.Single

interface CommonRepository {

    fun getGoals(): Maybe<List<String>>

    fun getTags(tagName: String): Single<Map<String, ArrayList<String>>>
}