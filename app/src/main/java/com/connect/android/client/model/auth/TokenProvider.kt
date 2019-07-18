package com.connect.android.client.model.auth

interface TokenProvider {
    fun getToken(): String?
}