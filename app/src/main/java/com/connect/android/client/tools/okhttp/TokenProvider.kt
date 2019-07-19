package com.connect.android.client.tools.okhttp

interface TokenProvider {
    fun getToken(): String?
}