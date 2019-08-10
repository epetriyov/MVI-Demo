package com.connect.android.client.modules.events

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.contacts.ContactsController
import com.connect.android.client.modules.events.info.EventInfoController

class EventsController(bundle: Bundle? = null) : BaseMviController<EventsView, EventsVIA, EventsVOA>(bundle) {
    override fun buildView(context: Context) = EventsView(context, EventsVS())

    override fun handleViewEvents(action: EventsVOA) {
        Do exhaustive when (action) {
            is EventsVOA.EventMembers -> router.pushController(ContactsController.eventMembers(action.eventId).buildRouterTransaction())
            is EventsVOA.EventInfo -> router.pushController(EventInfoController.newInstance(action.event).buildRouterTransaction())
        }
    }
}