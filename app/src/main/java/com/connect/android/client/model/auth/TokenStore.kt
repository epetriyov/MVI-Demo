package com.connect.android.client.model.auth

import android.content.SharedPreferences
import com.connect.android.client.tools.okhttp.TokenProvider

interface TokenStore : TokenProvider {

    fun saveToken(token: String)

    fun removeToken()
}

class SharedTokenStore(private val sharedPreferences: SharedPreferences) : TokenStore {
    companion object {
        private const val EXTRA_TOKEN = "token"
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(EXTRA_TOKEN, token).apply()
    }

    override fun removeToken() {
        sharedPreferences.edit().remove(EXTRA_TOKEN).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(EXTRA_TOKEN, null)
    }

}
