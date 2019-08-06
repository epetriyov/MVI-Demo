package com.connect.android.client.modules.profile

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.BaseMviController
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

    override fun buildView(context: Context): ProfileView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleViewEvents(action: ProfileVOA) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}