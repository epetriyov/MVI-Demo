package com.connect.android.client.modules.auth

import android.app.Activity
import android.content.Intent
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.api.model.VKScopes
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

enum class SocialType {
    VK, FB
}

data class SocialResult(val socialType: SocialType, val error: String? = null, val token: String? = null)

interface SocialHelper {
    fun loginViaVk()

    fun loginViaFb()

    fun loginResult(): Observable<SocialResult>

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}

class SocialHelperImpl(private val context: Activity) : SocialHelper {

    private val socialResults = PublishSubject.create<SocialResult>()

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    private val vkCallback = object : VKCallback<VKAccessToken> {
        override fun onResult(res: VKAccessToken) {
            socialResults.onNext(SocialResult(socialType = SocialType.VK, token = res.accessToken))
        }

        override fun onError(error: VKError) {
            socialResults.onNext(SocialResult(socialType = SocialType.VK, error = error.errorMessage))
        }
    }

    init {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : com.facebook.FacebookCallback<LoginResult> {
                override fun onError(error: FacebookException) {
                    socialResults.onNext(SocialResult(socialType = SocialType.FB, error = error.message))
                }

                override fun onSuccess(result: LoginResult) {
                    socialResults.onNext(SocialResult(socialType = SocialType.FB, token = result.accessToken.token))
                }

                override fun onCancel() {
                }

            })
    }

    override fun loginViaVk() {
        VKSdk.login(context, VKScopes.EMAIL)
    }

    override fun loginViaFb() {
        LoginManager.getInstance()
            .logInWithReadPermissions(context, Collections.singletonList("email"))
    }

    override fun loginResult() = socialResults

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, vkCallback)) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

}