package com.connect.android.client.modules.auth

import android.content.Intent
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import kotlinx.android.parcel.Parcelize

sealed class AuthVIA : ViewInputAction() {
    object AuthFbClicked : AuthVIA()
    object AuthVkClicked : AuthVIA()
    object AuthFbSdk : AuthVIA()
    object AuthVkSdk : AuthVIA()
    object SocialResult : AuthVIA()
    object Init : AuthVIA()
    object Progress : AuthVIA()
    object Success : AuthVIA()
    data class OnActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?) : AuthVIA()
    data class Error(val error: String) : AuthVIA()
    data class ProceedVk(val token: String) : AuthVIA()
    data class ProceedFb(val token: String) : AuthVIA()
}

sealed class AuthVOA : ViewOutputAction() {
    object AuthSuccess : AuthVOA()
}

@Parcelize
data class AuthVS(
    val error: ORField<String> = ORField.empty(),
    val progress: Boolean = false,
    val success: ORField<Unit> = ORField.empty()
) : ViewState()