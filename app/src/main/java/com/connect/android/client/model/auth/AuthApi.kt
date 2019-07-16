package com.connect.android.client.model.auth

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/facebook?isCallback=false")
    fun authFb(@Body token: AccessTokenRequest): Single<LoginResponse>

    @POST("/auth/vkontakte?isCallback=false")
    fun authVk(@Body token: AccessTokenRequest): Single<LoginResponse>

    @POST("/devices")
    fun sendToken(@Body tokenRequest: TokenRequest): Completable
}