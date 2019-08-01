package com.connect.android.client.model.auth

import com.squareup.moshi.Json

data class AccessTokenRequest(@Json(name = "access_token") val accessToken: String)

data class LoginResponse(
    @Json(name = "X-AUTH-USER-ID") val userId: String,
    @Json(name = "X-AUTH-TOKEN") val token: String
)

data class TokenRequest(
    @Json(name = "object") val obj: String, val environment: String,
    val model: String, val deviceUid: String, val deviceToken: String, val appVersion: String
)