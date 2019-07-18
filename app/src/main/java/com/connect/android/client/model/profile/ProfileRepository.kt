package com.connect.android.client.model.profile

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface ProfileRepository {

    fun fetchProfile(): Maybe<User>

    fun loadProfile(): Maybe<User>

    fun uploadAvatar(avatarUrl: String): Single<AvatarResponse>

    fun updateProfile(user: User): Completable
}

class ProfileRepoImpl(private val profileApi: ProfileApi, private val profileStore: ProfileStore) : ProfileRepository {
    override fun fetchProfile(): Maybe<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadProfile(): Maybe<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uploadAvatar(avatarUrl: String): Single<AvatarResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateProfile(user: User): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}