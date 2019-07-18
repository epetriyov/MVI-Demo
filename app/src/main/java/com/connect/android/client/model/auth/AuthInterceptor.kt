package com.connect.android.client.model.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {

    companion object {
        private const val TOKEN_HEADER = "X-AUTH-TOKEN"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(
            tokenProvider.getToken()?.let { request.newBuilder().addHeader(TOKEN_HEADER, it).build() } ?: request
        )
    }
}