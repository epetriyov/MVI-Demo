package com.connect.android.client.model.profile

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {
    @GET("/users/me")
    fun getProfile(): Single<Me>

    @POST("/users/me")
    fun updateUserInfo(@Body profile: Me): Completable

    @Multipart
    @POST("/users/me/avatar")
    fun uploadAvatar(@Part file: MultipartBody.Part): Single<AvatarResponse>
}