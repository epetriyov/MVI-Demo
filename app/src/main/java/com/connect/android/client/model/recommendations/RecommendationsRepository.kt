package com.connect.android.client.model.recommendations

import com.connect.android.client.model.profile.User
import io.reactivex.Maybe
import io.reactivex.Single

interface RecommendationsRepository {

    fun connectUser(userId: String): Single<ConnectState>

    fun declineUser(userId: String): Single<ConnectState>

    fun getRecommendations(): Maybe<List<User>>
}

class RecommendationsRepoImpl(private val recommendationsApi: RecommendationsApi) : RecommendationsRepository {
    override fun connectUser(userId: String): Single<ConnectState> {
        return recommendationsApi.connectUser(userId).map { ConnectState.valueOf(it.status) }
    }

    override fun declineUser(userId: String): Single<ConnectState> {
        return recommendationsApi.declineUser(userId).map { ConnectState.valueOf(it.status) }
    }

    override fun getRecommendations(): Maybe<List<User>> {
        return recommendationsApi.getRecommendations().map { it.data }
    }

}