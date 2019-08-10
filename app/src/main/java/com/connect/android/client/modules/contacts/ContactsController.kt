package com.connect.android.client.modules.contacts

import android.content.Context
import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.modules.base.BaseMviController

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

    override fun buildView(context: Context): ContactsView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleViewEvents(action: ContactsVOA) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}