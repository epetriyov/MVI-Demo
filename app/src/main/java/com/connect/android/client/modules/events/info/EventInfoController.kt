package com.connect.android.client.modules.events.info

import android.content.Context
import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.model.events.Event
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.contacts.ContactsController

class EventInfoController(bundle: Bundle? = null) :
    BaseMviController<EventInfoView, EventInfoVIA, EventInfoVOA>(bundle) {

    companion object {

        private const val EXTRA_EVENT = "extra_event"

        fun newInstance(event: Event): Controller {
            return EventInfoController(
                BundleBuilder(Bundle())
                    .putSerializable(EXTRA_EVENT, event)
                    .build()
            )
        }
    }

    override fun buildView(context: Context) = EventInfoView(
        context,
        EventInfoVS((args.getSerializable(EXTRA_EVENT) as Event).withUpdate())
    )

    override fun handleViewEvents(action: EventInfoVOA) {
        Do exhaustive when (action) {
            is EventInfoVOA.EventMembers -> router.pushController(ContactsController.eventMembers(action.eventId).buildRouterTransaction())
            EventInfoVOA.Back -> router.handleBack()
        }
    }
}