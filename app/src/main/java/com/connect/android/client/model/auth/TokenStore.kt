package com.connect.android.client.model.auth

import io.reactivex.Single

interface TokenStore {

    fun loadToken(): Single<String>

    fun saveToken(token: String)

    fun removeToken()
}