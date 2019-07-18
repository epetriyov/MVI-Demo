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