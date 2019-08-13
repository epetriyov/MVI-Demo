package com.connect.android.client.modules.auth

import android.app.Activity
import android.content.Intent
import com.connect.android.client.extensions.safeMessage
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
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

    private val vkCallback = object : VKAuthCallback {
        override fun onLogin(token: VKAccessToken) {
                        socialResults.onNext(SocialResult(socialType = SocialType.VK, token = token.accessToken))
        }

        override fun onLoginFailed(errorCode: Int) {
                        socialResults.onNext(SocialResult(socialType = SocialType.VK, error = errorCode.toString()))
        }
    }

    init {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : com.facebook.FacebookCallback<LoginResult> {
                override fun onError(error: FacebookException) {
                    Timber.e(error)
                    socialResults.onNext(SocialResult(socialType = SocialType.FB, error = error.safeMessage()))
                }

                override fun onSuccess(result: LoginResult) {
                    socialResults.onNext(SocialResult(socialType = SocialType.FB, token = result.accessToken.token))
                }

                override fun onCancel() {
                }

            })
    }

    override fun loginViaVk() {
        VK.login(context, arrayListOf(VKScope.EMAIL))
    }

    override fun loginViaFb() {
        LoginManager.getInstance()
            .logInWithReadPermissions(context, Collections.singletonList("email"))
    }

    override fun loginResult() = socialResults

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VK.onActivityResult(requestCode, resultCode, data, vkCallback)) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

}