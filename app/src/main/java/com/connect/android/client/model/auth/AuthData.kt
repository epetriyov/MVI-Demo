package com.connect.android.client.model.auth

import com.google.gson.annotations.SerializedName

data class AccessTokenRequest(@SerializedName("access_token") val accessToken: String)

data class LoginResponse(@SerializedName("X-AUTH-USER-ID") val userId: String,
                         @SerializedName("X-AUTH-TOKEN") val token: String)

data class TokenRequest(@SerializedName("object") val obj: String, val environment: String,
                        val model: String, val deviceUid: String, val deviceToken: String, val appVersion: String)