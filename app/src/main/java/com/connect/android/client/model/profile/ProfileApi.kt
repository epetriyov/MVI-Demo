package com.connect.android.client.model.profile

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {
    @GET("/users/me")
    fun getProfile(): Single<User>

    @POST("/users/me")
    fun updateUserInfo(@Body profile: User): Completable

    @Multipart
    @POST("/users/me/avatar")
    fun uploadAvatar(@Part file: MultipartBody.Part): Single<AvatarResponse>
}