package com.connect.android.client.model.profile

import com.connect.android.client.model.ListData
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {
    @GET("/users/me")
    fun getProfile(): Single<User>

    @POST("/users/me/location")
    fun sendLocation(@Body location: LocationData): Completable

    @POST("/users/me")
    fun updateUserInfo(@Body profile: User): Completable

    @Multipart
    @POST("/users/me/avatar")
    fun uploadAvatar(@Part file: MultipartBody.Part): Single<Response<AvatarResponse>>

    @GET("/users/me/location")
    fun getLocation(): Single<Response<LocationData>>

    @GET("/{tagName}")
    fun getTags(@Path("tagName") tagName: String): Single<Map<String, ArrayList<String>>>

    @GET("/goals")
    fun getGoals(): Single<Response<FieldsResponse>>
}