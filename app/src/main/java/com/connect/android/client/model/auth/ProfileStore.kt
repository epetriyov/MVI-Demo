package com.connect.android.client.model.auth

import android.content.SharedPreferences

interface ProfileStore {

    fun getUserId(): String?

    fun saveUserId(userId: String)

    fun removeUserId()
}

class SharedProfileStore(private val sharedPreferences: SharedPreferences) :
    ProfileStore {

    companion object {
        private const val KEY_USER_ID = "user_id"
    }

    override fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    override fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    override fun removeUserId() {
        sharedPreferences.edit().remove(KEY_USER_ID).apply()
    }

}