package com.connect.android.client.model.auth

import io.reactivex.Completable

interface AuthRepository {

    fun updateNotificationToken()

    fun isAuthorized(): Boolean

    fun logout()

    fun authVk(token: String): Completable

    fun authFb(token: String): Completable
}