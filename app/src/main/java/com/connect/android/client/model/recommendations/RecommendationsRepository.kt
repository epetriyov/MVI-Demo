package com.connect.android.client.model.recommendations

import com.connect.android.client.model.profile.User
import io.reactivex.Maybe
import io.reactivex.Single

interface RecommendationsRepository {

    fun connectUser(userId: String): Single<ConnectState>

    fun declineUser(userId: String): Single<ConnectState>

    fun getRecommendations(): Maybe<List<User>>
}