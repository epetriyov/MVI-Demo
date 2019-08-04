package com.connect.android.client.modules.start

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.modules.auth.AuthController
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.main.MainController

class StartController(bundle: Bundle? = null) : BaseMviController<StartView, StartVIA, StartVOA>(bundle) {
    override fun buildView(context: Context) = StartView(context, StartVS())

    override fun handleViewEvents(action: StartVOA) {
        router.setRoot(
            (Do exhaustive when (action) {
                StartVOA.MainAction -> MainController()
                StartVOA.LoginAction -> AuthController()
            })!!.buildRouterTransaction()
        )
    }
}