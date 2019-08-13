package com.connect.android.client.model.auth

import android.os.Build
import com.connect.android.client.BuildConfig
import com.connect.android.client.tools.room.ConnectDatabase
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Completable
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.SingleTransformer
import timber.log.Timber

interface AuthRepository {

    fun updateNotificationToken(): Completable

    fun pushNotificationToken(token: String): Completable

    fun isAuthorized(): Boolean

    fun logout()

    fun authVk(token: String): Completable

    fun authFb(token: String): Completable

    fun getUserId(): String?
}

class AuthRepoImpl(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val profileStore: ProfileStore,
    private val database: ConnectDatabase,
    private val firebaseInstanceId: FirebaseInstanceId
) : AuthRepository {

    private val authTransformer = SingleTransformer<LoginResponse, LoginResponse> { single ->
        single.doOnSuccess {
            tokenStore.saveToken(it.token)
            profileStore.saveUserId(it.userId)
        }
    }

    override fun pushNotificationToken(token: String): Completable {
        return authApi.sendToken(
            TokenRequest(
                "device", "ANDROID", Build.MODEL,
                firebaseInstanceId.id, token, BuildConfig.VERSION_NAME
            )
        )
    }

    override fun updateNotificationToken(): Completable {
        return Observable.create { emitter: Emitter<String> ->
            run {
                firebaseInstanceId.instanceId
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Timber.e(task.exception, "getInstanceId failed")
                            emitter.onComplete()
                        }
                        task.result?.token?.let {
                            emitter.onNext(it)
                        }
                    }
            }
        }.flatMapCompletable {
            pushNotificationToken(it)
        }
    }

    override fun isAuthorized(): Boolean {
        return tokenStore.getToken() != null
    }

    override fun logout() {
        tokenStore.removeToken()
        profileStore.removeUserId()
        database.clearAllTables()
    }

    override fun authVk(token: String): Completable {
        return authApi.authVk(AccessTokenRequest(token)).compose(authTransformer).ignoreElement()
    }

    override fun authFb(token: String): Completable {
        return authApi.authFb(AccessTokenRequest(token)).compose(authTransformer).ignoreElement()
    }

    override fun getUserId(): String? {
        return profileStore.getUserId()
    }

}