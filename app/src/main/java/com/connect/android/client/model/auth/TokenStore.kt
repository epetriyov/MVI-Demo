package com.connect.android.client.model.auth

import android.content.SharedPreferences
import com.connect.android.client.tools.okhttp.TokenProvider

interface TokenStore : TokenProvider {

    fun saveToken(token: String)

    fun removeToken()
}

class SharedTokenStore(private val sharedPreferences: SharedPreferences): TokenStore{
    override fun saveToken(token: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeToken() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getToken(): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
