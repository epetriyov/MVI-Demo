package com.connect.android.client.model.profile

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface ProfileRepository {

    fun loadProfile(): Flowable<Me>

    fun uploadAvatar(avatarUrl: String): Single<AvatarResponse>

    fun updateProfile(user: Me): Completable

    fun removeProfile(): Completable
}

class ProfileRepoImpl(private val profileApi: ProfileApi, private val profileDao: ProfileDao) : ProfileRepository {
    override fun removeProfile(): Completable {
        return loadProfile().flatMapCompletable { profileDao.deleteUser(it) }
    }

    override fun loadProfile(): Flowable<Me> {
        return profileDao.getUser().filter { it.isNotEmpty() }.map { it[0] }
    }

    override fun uploadAvatar(avatarUrl: String): Single<AvatarResponse> {
        val requestFile: RequestBody =
            RequestBody.create(
                MediaType.parse("image/*"),
                File(avatarUrl)
            )
        val multiPart = MultipartBody.Part.createFormData("avatar", "ava", requestFile)
        return profileApi.uploadAvatar(multiPart)
    }

    override fun updateProfile(user: Me): Completable {
        return profileApi.updateUserInfo(user)
    }

}