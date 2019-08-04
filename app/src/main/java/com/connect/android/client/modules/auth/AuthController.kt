package com.connect.android.client.modules.auth

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.main.MainController

class AuthController(bundle: Bundle? = null) : BaseMviController<AuthView, AuthVIA, AuthVOA>(bundle) {
    override fun buildView(context: Context) = AuthView(context, AuthVS())

    override fun handleViewEvents(action: AuthVOA) {
        router.setRoot(
            (Do exhaustive when (action) {
                AuthVOA.AuthSuccess -> MainController()
            })!!.buildRouterTransaction()
        )
    }
}