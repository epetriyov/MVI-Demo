package com.connect.android.client.model.profile

import io.reactivex.Maybe

interface ProfileStore {

    fun loadCurrentProfile(): Maybe<User>

    fun saveCurrentProfile(user: User)

    fun removeProfile()
}