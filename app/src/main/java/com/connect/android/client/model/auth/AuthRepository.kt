package com.connect.android.client.model.auth

import io.reactivex.Completable

interface AuthRepository {

    fun updateNotificationToken()

    fun isAuthorized(): Boolean

    fun logout()

    fun authVk(token: String): Completable

    fun authFb(token: String): Completable
}

class AuthRepoImpl(private val authApi: AuthApi, private val tokenStore: TokenStore): AuthRepository {
    override fun updateNotificationToken() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isAuthorized(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun authVk(token: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun authFb(token: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}