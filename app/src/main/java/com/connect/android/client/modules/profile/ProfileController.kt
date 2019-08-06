package com.connect.android.client.modules.profile

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.chat.ChatController

class ProfileController(bundle: Bundle? = null) : BaseMviController<ProfileView, ProfileVIA, ProfileVOA>(bundle) {

    companion object {
        private const val EXTRA_USER = "extra_user"

        fun newInstance(user: User): ChatController {
            return ChatController(
                BundleBuilder(Bundle())
                    .putSerializable(EXTRA_USER, user)
                    .build()
            )
        }
    }

    override fun buildView(context: Context) =
        ProfileView(context, ProfileVS((args.getSerializable(EXTRA_USER) as User).withUpdate()))

    override fun handleViewEvents(action: ProfileVOA) {
        Do exhaustive when (action) {
            ProfileVOA.BackAction -> router.handleBack()
            is ProfileVOA.ChatAction -> router.pushController(ChatController.newInstance(action.chat).buildRouterTransaction())
        }
    }
}