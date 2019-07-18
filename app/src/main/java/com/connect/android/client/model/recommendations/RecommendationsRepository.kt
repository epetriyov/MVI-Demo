package com.connect.android.client.model.recommendations

import com.connect.android.client.model.profile.User
import io.reactivex.Maybe
import io.reactivex.Single

interface RecommendationsRepository {

    fun connectUser(userId: String): Single<ConnectState>

    fun declineUser(userId: String): Single<ConnectState>

    fun getRecommendations(): Maybe<List<User>>
}

class RecommendationsRepoImpl(private val recommendationsApi: RecommendationsApi): RecommendationsRepository
{
    override fun connectUser(userId: String): Single<ConnectState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun declineUser(userId: String): Single<ConnectState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecommendations(): Maybe<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}