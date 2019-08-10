package com.connect.android.client.modules.contacts

import android.content.Context
import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.chat.ChatController
import com.connect.android.client.modules.profile.ProfileController

class ContactsController(bundle: Bundle? = null) : BaseMviController<ContactsView, ContactsVIA, ContactsVOA>(bundle) {

    companion object {

        private const val EXTRA_EVENT_ID = "event_id"

        fun eventMembers(eventId: String): Controller {
            return ContactsController(
                BundleBuilder(Bundle())
                    .putString(EXTRA_EVENT_ID, eventId)
                    .build()
            )
        }
    }

    override fun buildView(context: Context) = ContactsView(
        context,
        ContactsVS(args.getString(EXTRA_EVENT_ID))
    )

    override fun handleViewEvents(action: ContactsVOA) {
        Do exhaustive when (action) {
            ContactsVOA.Back -> router.handleBack()
            is ContactsVOA.UserSelect -> router.pushController(ProfileController.newInstance(action.user).buildRouterTransaction())
            is ContactsVOA.ChatCreate -> router.pushController(ChatController.newInstance(action.chat).buildRouterTransaction())
        }
    }
}